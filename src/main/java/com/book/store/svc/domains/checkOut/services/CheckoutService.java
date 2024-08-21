package com.book.store.svc.domains.checkOut.services;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.exceptions.PaymentException;
import com.book.store.svc.commons.utils.DecimalUtil;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.cart.services.CartService;
import com.book.store.svc.domains.checkOut.dtos.CheckOutRequest;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.CheckOut;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.repos.CheckoutRepo;
import com.book.store.svc.domains.checkOut.services.TransactionRecordService;
import com.book.store.svc.domains.checkOut.services.payment.factory.PaymentServiceFactory;
import com.book.store.svc.domains.customer.db.models.Customer;
import com.book.store.svc.domains.customer.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutService {
    private final CheckoutRepo checkoutRepo;
    private final CartService cartService;
    private final CustomerService customerService;
    private final TransactionRecordService transactionRecordService;
    private final PaymentServiceFactory paymentServiceFactory;

    @Value("${service.is.vat.enabled:false}")
    private boolean isVatEnabled;

    @Value("${service.vat.percentage:7.5}")
    private BigDecimal vatPercentage;

    @Transactional(rollbackOn = Exception.class)
    public CheckOut createCheckout(String customerCode, CheckOutRequest request) throws NotFoundException {
        var customer = customerService.findByCode(customerCode);
        var cart = cartService.getCartDetails(request.getCartCode());
        var checkOut = buildCheckout(customerCode, request, cart);
        checkoutRepo.save(checkOut);

        var transaction = buildTransaction(checkOut, customer, cart.getTotal());
        updateTransactionAmount(transaction, cart.getTotal());
        transactionRecordService.save(transaction);

        log.info("About to post check-out transaction request to payment provider");
        try{
            var paymentService = paymentServiceFactory.getPaymentServiceProvider(request.getPaymentChannel());
            paymentService.postPayment(transaction);
        }catch (PaymentException e){
            log.error("Payment error: {}" , e);
            transaction.setPaymentStatus(e.getPaymentStatus());
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("Payment error {}", e);
            transaction.setPaymentStatus(EPaymentStatus.FAILED);
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setMessage(e.getMessage());
        }

        // update transaction info after post of payment request
        transactionRecordService.save(transaction);

        // retrieve afresh the check-out details
        return getCheckout(checkOut.getCode());
    }

    public List<EPaymentChannel> getPaymentChannels(){
        return List.of(EPaymentChannel.values());
    }

    public CheckOut getCheckout(String checkOutCode) throws NotFoundException {
        return checkoutRepo.findByCode(checkOutCode).orElseThrow(()->
                new NotFoundException(String.format("No checkout found with this code", checkOutCode)));
    }

    // local functions
    private CheckOut buildCheckout(String customerCode, CheckOutRequest request, Cart cart){
        return new CheckOut(cart, request.getPaymentChannel());
    }
    private TransactionRecord buildTransaction(CheckOut checkOut, Customer customer, BigDecimal amount){
        return new TransactionRecord(checkOut, customer, amount);
    }
    private BigDecimal getVat(BigDecimal amount){
        if(isVatEnabled){
            return BigDecimal.ZERO;
        }
        return DecimalUtil.standardRound(amount.multiply(vatPercentage.divide(BigDecimal.valueOf(100))));
    }
    private void updateTransactionAmount(TransactionRecord transactionRecord, BigDecimal requestAmount){
        transactionRecord.setRequestAmount(requestAmount);
        transactionRecord.setVat(getVat(transactionRecord.getRequestAmount()));
        transactionRecord.setAmount(transactionRecord.getRequestAmount().add(transactionRecord.getVat()));
    }
}
