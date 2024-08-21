package com.book.store.svc.domains.checkOut.services.payment.strategies;

import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.services.payment.PaymentService;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Typically, there may be multiple gateways providing bank transfer payments,
 * so in such case one can have a table to resolve the right or best fit gateway based on success-rate or priority algorithm configs.
 * Nonetheless, in this test. We will have just one default implementation for bankTransfer.
 */
public interface BankTransferPaymentService extends PaymentService {
    @Override
    default EPaymentChannel getPaymentChannel() {
        return EPaymentChannel.BANK_TRANSFER;
    }

    @Override
    default void postPayment(TransactionRecord transactionRecord) {
        throw new NotImplementedException("Bank Transfer based Payment not implemented here");
    }
}
