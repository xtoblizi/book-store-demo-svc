package com.example.demo.domains.cart.services;

import com.example.demo.commons.exceptions.NotFoundException;
import com.example.demo.domains.cart.db.models.CartItem;
import com.example.demo.domains.cart.repos.CartItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
