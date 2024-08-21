package com.example.demo.domains.checkOut.services.payment.strategies;

import com.example.demo.domains.checkOut.enums.EPaymentChannel;
import com.example.demo.domains.checkOut.models.TransactionRecord;
import com.example.demo.domains.checkOut.services.payment.PaymentService;
import org.apache.commons.lang3.NotImplementedException;


/**
 * Typically, there may be multiple gateways providing ussd transfer payments,
 * so in such case one can have a table to resolve the right or best fit gateway based on success-rate or priority algorithm configs.
 * However, in this test. We will have just one default implementation for ussd payment.
 */
public interface UssdPaymentService extends PaymentService {
    @Override
    default EPaymentChannel getPaymentChannel() {
        return EPaymentChannel.USSD;
    }

    @Override
    default void postPayment(TransactionRecord transactionRecord) {
        throw new NotImplementedException("USSD Payment not implemented here");
    }
}
