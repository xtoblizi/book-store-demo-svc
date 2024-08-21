package com.example.demo.domains.cart.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest{
    @NotBlank(message = "Book Product code cannot be empty")
    private String bookProductCode;
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;
}
