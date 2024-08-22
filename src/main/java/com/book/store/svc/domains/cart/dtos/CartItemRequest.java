package com.book.store.svc.domains.cart.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest{
    @NotBlank(message = "Book Product code cannot be empty")
    private String bookProductCode;
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

}
