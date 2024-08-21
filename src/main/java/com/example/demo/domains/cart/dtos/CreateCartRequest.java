package com.example.demo.domains.cart.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateCartRequest {
    @NotBlank(message = "Kindly provide the customer info")
    private String customerCode;
    @NotNull(message = "Cart items cannot be empty")
    private List<CartItemRequest> cartItems;
}