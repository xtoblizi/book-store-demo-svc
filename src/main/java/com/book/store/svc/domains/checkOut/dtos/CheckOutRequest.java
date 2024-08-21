package com.book.store.svc.domains.checkOut.dtos;

import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckOutRequest {
    @NotNull(message = "Payment channel")
    private EPaymentChannel paymentChannel;
    @NotBlank(message = "Cart code is required")
    private String cartCode;
    private String note;
}
