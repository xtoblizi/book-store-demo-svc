package com.book.store.svc.domains.checkOut.dtos.callbacks;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WebTransactionCallbackDto {

    /**
     * This typically is subject to the payment provider documentation.
     * However, let's assume that successful transaction(s) will be 00, while unsuccessful will range amongst[ 99, 91 , 63 ] etc
     */
    private String responseCode;
    private BigDecimal amount;
    private BigDecimal charge;
    private String authId;
}

