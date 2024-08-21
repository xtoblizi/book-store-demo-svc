package com.example.demo.domains.checkOut.services.payment.concretes;

import com.example.demo.domains.checkOut.enums.EPaymentProvider;
import com.example.demo.domains.checkOut.models.TransactionRecord;
import com.example.demo.domains.checkOut.services.payment.strategies.WebPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    }
}
