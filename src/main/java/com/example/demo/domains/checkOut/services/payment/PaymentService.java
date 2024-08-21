package com.example.demo.domains.checkOut.services.payment;

import com.example.demo.commons.exceptions.PaymentException;
import com.example.demo.domains.checkOut.enums.EPaymentChannel;
import com.example.demo.domains.checkOut.enums.EPaymentProvider;
import com.example.demo.domains.checkOut.models.TransactionRecord;

public interface PaymentService {
    EPaymentProvider paymentProvider();
    EPaymentChannel getPaymentChannel();

    void postPayment(TransactionRecord transactionRecord) throws PaymentException;
}
