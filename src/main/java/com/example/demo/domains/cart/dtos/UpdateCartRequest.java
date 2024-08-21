package com.example.demo.domains.cart.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCartRequest extends CreateCartRequest{
    private String cartCode;
}
