package com.book.store.svc.domains.cart.dtos;

import com.book.store.svc.domains.cart.dtos.CartItemRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartRequest {
    @NotBlank(message = "Kindly provide the customer info")
    private String customerCode;
    @NotNull(message = "Cart items cannot be empty")
    private List<CartItemRequest> cartItems = new ArrayList<>();
}