package com.example.demo.domains.checkOut.dtos.callbacks;

import lombok.Data;

@Data
public class USSDCallBackDto{
    private String bank;
    private String shortCode;
    private String transactionReference;
    private String responseCode;
    private String mnc;
    private String mcc;
    private String customerPhone;
}
