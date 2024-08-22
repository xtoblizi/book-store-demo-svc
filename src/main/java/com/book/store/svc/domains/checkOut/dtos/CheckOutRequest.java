package com.book.store.svc.domains.checkOut.dtos;

import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutRequest {
    @NotNull(message = "Payment channel")
    private EPaymentChannel paymentChannel;
    @NotBlank(message = "Cart code is required")
    private String cartCode;
    private String note;
}
