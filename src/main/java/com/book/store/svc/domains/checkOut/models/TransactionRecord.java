package com.book.store.svc.domains.checkOut.models;

import com.book.store.svc.commons.converters.LocalDateTimeConverter;
import com.book.store.svc.commons.entities.BaseEntity;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.CheckOut;
import com.book.store.svc.domains.customer.db.models.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Table(name = "transaction_records")
@Entity
@NoArgsConstructor
public class TransactionRecord extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_out_code", referencedColumnName = "code", nullable = false)
    private CheckOut checkOut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_code", referencedColumnName = "code", nullable = false)
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String transactionReference = UUID.randomUUID().toString();
    @Column(nullable = false)
    private BigDecimal requestAmount;
    @Column(nullable = false)
    private BigDecimal amount; // requestAmount + vat
    private BigDecimal vat;
    private String paymentProvider;
    private String message; // error or success message
    
    private String providerTransactionReference;
    private String authId;
    private String mti;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime requestTime;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime responseTime;
    private EPaymentStatus paymentStatus;
    private String responseCode;
    @Enumerated(EnumType.STRING)
    private EPaymentChannel paymentChannel;
    /**
     * Serialize and log the entire call back log received for this transaction.
     * Useful for troubleshooting, monitoring and audit.
     */
    private String callbackResponseData;
    /**
     * Serialize and log the entire data sent to payment gateway.
     * This is useful of auditing and monitoring
     */
    private String requestDataPosted;

    public TransactionRecord(CheckOut checkOut, Customer customer, BigDecimal requestAmount){
        this.checkOut = checkOut;
        this.customer = customer;
        this.requestAmount = requestAmount;
        this.amount = requestAmount.add(this.vat);
        this.paymentProvider = paymentProvider;
        this.message = "Processing";
        this.paymentStatus = EPaymentStatus.PENDING;
        this.requestTime = LocalDateTime.now();
    }

}
