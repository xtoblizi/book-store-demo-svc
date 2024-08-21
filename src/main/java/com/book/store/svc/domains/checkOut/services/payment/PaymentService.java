package com.book.store.svc.domains.checkOut.services.payment;

import com.book.store.svc.commons.exceptions.PaymentException;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.enums.EPaymentProvider;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;

public interface PaymentService {
    EPaymentProvider paymentProvider();
    EPaymentChannel getPaymentChannel();

    void postPayment(TransactionRecord transactionRecord) throws PaymentException;
}
