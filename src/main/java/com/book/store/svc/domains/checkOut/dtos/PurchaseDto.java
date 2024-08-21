package com.book.store.svc.domains.checkOut.dtos;

import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDto {

    // cart info
    private String cartCode;
    private String customerFirstName;
    private String customerLastName;
    private EPaymentChannel paymentChannel;
    private boolean isCheckedOut;

    // transaction info
    private String transactionReference;
    private BigDecimal amount;
    private String paymentProvider;
    private String message; // error or success message
    private String responseCode;
    private EPaymentStatus paymentStatus;

    // transaction provider info
    private String providerTransactionReference;
    private LocalDateTime paidAt;

    public PurchaseDto(TransactionRecord transactionRecord){
        this.cartCode = transactionRecord.getCheckOut().getCart().getCode();
        this.customerFirstName = transactionRecord.getCustomer().getFirstName();
        this.customerLastName = transactionRecord.getCustomer().getLastName();
        this.paymentChannel = transactionRecord.getCheckOut().getPaymentChannel();
        this.amount = transactionRecord.getAmount();

        this.transactionReference = transactionRecord.getTransactionReference();
        this.paymentProvider = transactionRecord.getPaymentProvider();
        this.message = transactionRecord.getMessage();
        this.paymentStatus = transactionRecord.getPaymentStatus();

        // transaction provider info
        this.providerTransactionReference = transactionRecord.getProviderTransactionReference();
        this.paidAt = transactionRecord.getCreatedAt();
        this.responseCode = transactionRecord.getResponseCode();
    }
}
