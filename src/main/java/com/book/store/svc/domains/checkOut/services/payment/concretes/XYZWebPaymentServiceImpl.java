package com.book.store.svc.domains.checkOut.services.payment.concretes;

import com.book.store.svc.domains.checkOut.enums.EPaymentProvider;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.services.payment.strategies.WebPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class XYZWebPaymentServiceImpl implements WebPaymentService {
    @Override
    public EPaymentProvider paymentProvider() {
        return EPaymentProvider.XYZ_CARD;
    }

    @Override
    public void postPayment(TransactionRecord transactionRecord) {
        // process payment and
        // make api call to the payment resource for XYZ Web Payment Gateway.

        transactionRecord.setProviderTransactionReference(UUID.randomUUID().toString());
        transactionRecord.setPaymentProvider(EPaymentProvider.XYZ_CARD.toString());
        transactionRecord.setMessage("Processing");
        transactionRecord.setPaymentStatus(EPaymentStatus.PROCESSING);
    }
}
