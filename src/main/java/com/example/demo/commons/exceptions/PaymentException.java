package com.example.demo.commons.exceptions;

import com.example.demo.domains.checkOut.enums.EPaymentStatus;
import lombok.Getter;

@Getter
public class PaymentException extends ServiceException{

    private EPaymentStatus paymentStatus = EPaymentStatus.INCONCLUSIVE;
    public PaymentException(String message, EPaymentStatus paymentStatus) {
        super(500, message);
        this.paymentStatus = paymentStatus;
    }

    public PaymentException(int code, String message) {
        super(code, message);
    }

    public PaymentException(int code, String message, EPaymentStatus paymentStatus) {
        super(code, message);
        this.paymentStatus = paymentStatus;
    }
}
