package com.book.store.svc.domains.checkOut.services.payment.concretes;

import com.book.store.svc.commons.exceptions.PaymentException;
import com.book.store.svc.domains.checkOut.enums.EPaymentProvider;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.services.payment.strategies.BankTransferPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ABCBankPaymentServiceImpl implements BankTransferPaymentService {
    @Override
    public EPaymentProvider paymentProvider() {
        return EPaymentProvider.ABC_BANk;
    }

    @Override
    public void postPayment(TransactionRecord transactionRecord) {
        // process bank payment and update transaction record object.
        // save/commit of changes will be done on caller method.

    }
}
