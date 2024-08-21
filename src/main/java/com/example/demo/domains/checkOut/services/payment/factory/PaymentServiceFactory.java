package com.example.demo.domains.checkOut.services.payment.factory;

import com.example.demo.domains.checkOut.enums.EPaymentChannel;
import com.example.demo.domains.checkOut.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceFactory {
    private final Set<PaymentService> paymentServices;

    /**
     * Here, we will work with the fact that we just have one payment gateway for each payment channel.
     * It real life in some cases, we may have more than one gateway per channel.
     * Thus, in such cases gateway resolution will be by channel, by successRate or by channel by priority config etc.
     * @param paymentChannel
     * @return
     */
    public PaymentService getPaymentServiceProvider(EPaymentChannel paymentChannel){
        for(PaymentService paymentService : paymentServices){
            if(paymentService.getPaymentChannel() == paymentChannel){
                return paymentService;
            }
        }
        throw new NotImplementedException(String.format("No concrete payment gateway implementation found for this payment channel: %s", paymentChannel));
    }
}
