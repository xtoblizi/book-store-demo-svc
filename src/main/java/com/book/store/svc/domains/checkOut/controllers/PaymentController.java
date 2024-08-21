package com.book.store.svc.domains.checkOut.controllers;

import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.domains.checkOut.dtos.PurchaseDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.BankTransferCallbackDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.USSDCallBackDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.WebTransactionCallbackDto;
import com.book.store.svc.domains.checkOut.services.TransactionRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final TransactionRecordService transactionRecordService;

    @GetMapping("/purchases/customer/{customerCode}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PurchaseDto> getPurchases(@PathVariable(name = "customerCode") String customerCode,
                                          @RequestParam(name = "page", defaultValue = "0")int page,
                                          @RequestParam(name = "size", defaultValue = "20") int size) {

        return transactionRecordService.findPurchases(customerCode, page, size);
    }


    /**
     * Typically all web callbacks will be specific to the payment provider/gateway.
     * However, for this mock service we will have them general level.
     * @param transactionReference
     * @param model
     * @throws ServiceException
     */
    @PutMapping("/web/call-back/{transactionReference}")
    @ResponseStatus(HttpStatus.OK)
    public void webTransactionCallback(@PathVariable(name = "transactionReference") String transactionReference,
                                       @RequestBody WebTransactionCallbackDto model) throws ServiceException {

        transactionRecordService.webTransactionCallBackUpdate(transactionReference, model);
    }

    /**
     * Typically all bank transfer callbacks will be specific to the payment provider/gateway.
     * Nonetheless, for this mock service we will have them general level.
     * @param transactionReference
     * @param model
     * @throws ServiceException
     */
    @PutMapping("/bank-transfer/call-back/{transactionReference}")
    @ResponseStatus(HttpStatus.OK)
    public void bankTransferCallback(@PathVariable(name = "transactionReference") String transactionReference,
                                       @RequestBody BankTransferCallbackDto model) throws ServiceException {

        transactionRecordService.bankTransactionCallBackUpdate(transactionReference, model);
    }

    /**
     * Typically all ussd callbacks will be specific to the payment provider/gateway ussd integrations specs.
     * However, for this mock book store service we will have one general callback.
     * @param transactionReference
     * @param model
     * @throws ServiceException
     */
    @PutMapping("/ussd-transfer/call-back/{transactionReference}")
    @ResponseStatus(HttpStatus.OK)
    public void ussdPaymentCallback(@PathVariable(name = "transactionReference") String transactionReference,
                                     @RequestBody USSDCallBackDto model) throws ServiceException {

        transactionRecordService.ussdTransactionCallBackUpdate(transactionReference, model);
    }
}
