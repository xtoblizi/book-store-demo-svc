package com.book.store.svc.domains.cart.services;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.domains.cart.db.models.CartItem;
import com.book.store.svc.domains.cart.db.repos.CartItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepo cartItemRepo;

    public CartItem save(CartItem cartItem){
        return cartItemRepo.save(cartItem);
    }

    public void delete(CartItem cartItem){
        cartItemRepo.delete(cartItem);
    }

    public CartItem findByCode(String code) throws NotFoundException {
        return cartItemRepo.findByCode(code).orElseThrow(()->
                new NotFoundException(String.format("No cart item found by this code: %s", code)));
    }

    public Set<CartItem> findCartItemsByCartCode(String cartCode) throws NotFoundException {
        return cartItemRepo.findAllByCart_Code(cartCode);
    }
}
