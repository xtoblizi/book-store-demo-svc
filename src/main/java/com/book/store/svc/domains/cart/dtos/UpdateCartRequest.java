package com.book.store.svc.domains.cart.dtos;

import com.book.store.svc.domains.cart.dtos.CreateCartRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCartRequest extends CreateCartRequest {
    private String cartCode;
}
