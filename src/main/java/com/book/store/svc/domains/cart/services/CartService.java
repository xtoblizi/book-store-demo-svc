package com.book.store.svc.domains.cart.services;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.exceptions.ServiceValidationException;
import com.book.store.svc.commons.utils.DecimalUtil;
import com.book.store.svc.commons.utils.JsonConverter;
import com.book.store.svc.domains.book.services.BookProductService;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.cart.db.models.CartItem;
import com.book.store.svc.domains.cart.dtos.CartItemRequest;
import com.book.store.svc.domains.cart.dtos.CreateCartRequest;
import com.book.store.svc.domains.cart.repos.CartRepo;
import com.book.store.svc.domains.cart.services.CartItemService;
import com.book.store.svc.domains.customer.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
// TODO: Tip: You can utilize redis to cache cart item for customers with 24hrs to 72 hrs sliding option depending.
public class CartService {
    private final CartRepo cartRepo;
    private final CartItemService cartItemService;
    private final CustomerService customerService;
    private final BookProductService bookProductService;

    @Transactional(rollbackOn = Exception.class)
    public Cart createCart(String customerCode, CreateCartRequest model) throws ServiceValidationException {
        if (Objects.isNull(model)) throw new ServiceValidationException("Cart request model cannot be empty");

        // validate customer Code
        var customer = customerService.findByCode(customerCode);

        // cart
        Cart cart = null;
        Optional<Cart> cartOptional = cartRepo.findPendingCartByCustomerAndIncludeItems(customer.getCode());
        if (cartOptional.isPresent()) {
            log.warn("A cart already exist for this customer which is pending and yet to check-out");
            var existingObj = JsonConverter.toJson(cartOptional.get());
            throw new ServiceValidationException(412,
                    "There is a pending cart for this customer. Kindly preview and handle existing cart",
                    existingObj);
        }
        cart = new Cart(customer);
        cartRepo.save(cart);

        // validate cart items..
        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemRequest itemRequest : model.getCartItems()) {
            var bookProduct = bookProductService.findByCode(itemRequest.getBookProductCode());
            var cartItem =  new CartItem(bookProduct, cart, itemRequest.getQuantity());
            cartItems.add(cartItemService.save(cartItem));
        }

        // update cart with total and total dispute info
        cart.setGrossTotal(computeTotalPrice(cartItems));
        cart.setGrossTotal(computeTotalDiscount(cartItems));
        cart.setTotal(cart.getGrossTotal().subtract(cart.getTotalDiscount()));
        cartRepo.save(cart);

        cart.setCartItems(cartItems);
        return cart;
    }


    @Transactional(rollbackOn = Exception.class)
    public Cart addItemToCart(String cartCode, CartItemRequest cartItemRequest) throws NotFoundException {
        var cart = cartRepo.findByCodeIncludeItems(cartCode).orElseThrow(()-> new NotFoundException(String.format("Cart could not be found with code", cartCode)));
        var bookProduct = bookProductService.findByCode(cartItemRequest.getBookProductCode());

        var cartItems = cart.cartItems != null? cart.cartItems : new HashSet<CartItem>();
        var cartItemFiltered =  cartItems.stream().filter(x->x.getProduct().getCode().equals(cartItemRequest.getBookProductCode())).collect(Collectors.toList());
        if(!cartItemFiltered.isEmpty()){
            // update item in cart based on quantity info.
            var cartItem = cartItemFiltered.getFirst();
            if(!cartItem.getQuantity().equals(cartItemRequest.getQuantity())){
                cartItem.setQuantity(cartItemRequest.getQuantity());
                cartItemService.save(cartItem);
            }
        }else{
            // create new cart item and add to cart
            cartItemService.save(new CartItem(bookProduct, cart, cartItemRequest.getQuantity()));
        }

        // retrieve afresh for sync cart items and return cart
        return getCartDetails(cartCode);
    }

    @Transactional(rollbackOn = Exception.class)
    public Cart removeItemFromCart(String cartCode, String cartItemCode) throws NotFoundException{
        cartRepo.findByCodeIncludeItems(cartCode)
                .orElseThrow(()-> new NotFoundException(String.format("Cart could not be found with code", cartCode)));
        var cartITem = cartItemService.findByCode(cartItemCode);

        cartItemService.delete(cartITem);
        // retrieve card afresh
        return getCartDetails(cartCode);
    }

    public Cart getCartDetails(String cartCode) throws NotFoundException {
        var cart = cartRepo.findByCodeIncludeItems(cartCode).orElseThrow(()-> new NotFoundException(String.format("Cart could not be found with code", cartCode)));
        cart.setTotalDiscount(computeTotalDiscount(cart.cartItems));
        cart.setTotalDiscount(computeTotalPrice(cart.cartItems));

        return cart;
    }

    public Cart getCartForCustomer(String customerCode) throws NotFoundException {
        return cartRepo.findPendingCartByCustomerAndIncludeItems(customerCode)
                .orElseThrow(()-> new NotFoundException("No pending cart found for this customer," +
                        " feel free to create a new cart fot this customer"));

    }

    private BigDecimal computeTotalPrice(Set<CartItem> cartItems){
        BigDecimal priceSum = BigDecimal.ZERO;
        for (CartItem cartItem: cartItems){
            if(Objects.isNull(cartItem.getProduct()))
                throw new RuntimeException("Cart item Product detail cannot be null when computing total price of cart items");
            priceSum = priceSum.add(cartItem.getProduct().getPrice());
        }
        return DecimalUtil.standardRound(priceSum);
    }

    private BigDecimal computeTotalDiscount(Set<CartItem> cartItems){
        BigDecimal discountSum = BigDecimal.ZERO;
        for (CartItem cartItem: cartItems){
            if(Objects.isNull(cartItem.getProduct()))
                throw new RuntimeException("Cart item's product detail cannot be null when computing total discount of cart items");
            discountSum = discountSum.add(cartItem.getProduct().getDiscount());
        }
        return DecimalUtil.standardRound(discountSum);
    }
}
