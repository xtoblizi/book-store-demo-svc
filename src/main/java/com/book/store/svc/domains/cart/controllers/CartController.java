package com.book.store.svc.domains.cart.controllers;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.cart.dtos.CartItemRequest;
import com.book.store.svc.domains.cart.dtos.CreateCartRequest;
import com.book.store.svc.domains.cart.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createCart(@Valid @RequestBody CreateCartRequest cartRequest) throws ServiceException {
        return cartService.createCart(cartRequest.getCustomerCode(), cartRequest);
    }


    @PostMapping("/{cartCode}/cart-item")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addItemToCart(@PathVariable(name = "cartCode") String cartCode, @Valid @RequestBody CartItemRequest cartRequest) throws ServiceException {
        return cartService.addItemToCart(cartCode, cartRequest);
    }


    @DeleteMapping("/{cartCode}/cart-item")
    @ResponseStatus(HttpStatus.OK)
    public Cart removeItemFromCart(@PathVariable(name = "cartCode") String cartCode,
                                   @RequestParam(name = "cartITemCode") String cartITemCode) throws ServiceException {
        return cartService.removeItemFromCart(cartCode, cartITemCode);
    }


    @GetMapping("/{cartCode}")
    @ResponseStatus(HttpStatus.OK)
    public Cart getCartByCode(@PathVariable(name = "cartCode") String cartCode) throws ServiceException {
        return cartService.getCartDetails(cartCode);
    }

    @GetMapping("/customer/{customerCode}")
    @ResponseStatus(HttpStatus.OK)
    public Cart getUnprocessedCartForCustomer(@PathVariable(name = "customerCode") String customerCode) throws NotFoundException {
        return cartService.getCartForCustomer(customerCode);
    }

}
