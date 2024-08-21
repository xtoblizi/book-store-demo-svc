package com.book.store.svc.domains.checkOut.services.payment.concretes;

import com.book.store.svc.domains.checkOut.enums.EPaymentProvider;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.services.payment.strategies.UssdPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class XNetworkUssdPaymentServiceImpl implements UssdPaymentService {
    @Override
    public EPaymentProvider paymentProvider() {
        return EPaymentProvider.X_NETWORK;
    }

    @Override
    public void postPayment(TransactionRecord transactionRecord) {

        transactionRecord.setProviderTransactionReference(UUID.randomUUID().toString());
        transactionRecord.setPaymentProvider(EPaymentProvider.X_NETWORK.toString());
        transactionRecord.setMessage("Processing");
        transactionRecord.setPaymentStatus(EPaymentStatus.PROCESSING);
    }
}
