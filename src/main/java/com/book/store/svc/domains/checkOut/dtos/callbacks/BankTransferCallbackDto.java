package com.book.store.svc.domains.checkOut.dtos.callbacks;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankTransferCallbackDto{
    private String responseCode;
    private String bankReference;
    private LocalDateTime transactionTime;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private String debitAccountNumber;
    private String creditAccountNumber;
    private String debitBankCode;
    private String creditBankCode;
    private String transactionSignature;
}
