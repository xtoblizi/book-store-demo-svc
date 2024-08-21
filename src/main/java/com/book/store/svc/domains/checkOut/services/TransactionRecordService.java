package com.book.store.svc.domains.checkOut.services;

import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.commons.utils.JsonConverter;
import com.book.store.svc.commons.utils.SortUtil;
import com.book.store.svc.domains.checkOut.dtos.PurchaseDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.BankTransferCallbackDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.USSDCallBackDto;
import com.book.store.svc.domains.checkOut.dtos.callbacks.WebTransactionCallbackDto;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.enums.EPaymentStatus;
import com.book.store.svc.domains.checkOut.models.TransactionRecord;
import com.book.store.svc.domains.checkOut.repos.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionRecordService {
    private final TransactionRepo transactionRepo;

    @Value("${web.transfer.failure.codes:91,99,63}")
    private String webFailureCodes;

    @Value("${web.transfer.success.code:00}")
    private String webTransferSuccessCode;
    public TransactionRecord save(TransactionRecord transactionRecord){
        return transactionRepo.save(transactionRecord);
    }
    public Page<PurchaseDto> findPurchases(String customerCode, int page, int size){
        Pageable pageable = SortUtil.getSortedPageable(page, size, SortDirection.DESCENDING, "");
        var pagedResponse = transactionRepo.findByCustomerInfo(customerCode, pageable);
        return getResponses(pagedResponse);
    }


    // call back payment transaction update for web transfers
    @Async
    public void webTransactionCallBackUpdate(String transactionReference, WebTransactionCallbackDto model) throws ServiceException {
        // find
        var transactionOpt = transactionRepo.findByProviderTransactionReferenceAndPaymentChannel(transactionReference, EPaymentChannel.WEB);
        if(transactionOpt.isEmpty()){
            log.error(String.format("Transaction received with provider reference: %s which could not be found", transactionReference));
            // perform other alerting functions.
            throw new ServiceException(String.format("Transaction not found by provider reference %s", transactionReference));
        }

        var transaction = transactionOpt.get();
        transaction.setPaymentStatus(getPaymentStatus(model.getResponseCode()));
        transaction.setResponseCode(model.getResponseCode());
        transaction.setResponseTime(LocalDateTime.now());
        transaction.setCallbackResponseData(JsonConverter.toJson(model));


        transactionRepo.save(transaction);
    }

    @Async
    public void bankTransactionCallBackUpdate(String providerTransactionReference, BankTransferCallbackDto model) throws ServiceException {
        // find
        var bankTranxOpt = transactionRepo.findByProviderTransactionReferenceAndPaymentChannel(providerTransactionReference, EPaymentChannel.BANK_TRANSFER);
        if(bankTranxOpt.isEmpty()){
            log.error(String.format("Transaction received with provider reference: %s which could not be found", providerTransactionReference));
            // perform other alerting functions.
            throw new ServiceException(String.format("Transaction not found by provider reference %s", providerTransactionReference));
        }

        var transaction = bankTranxOpt.get();
        transaction.setPaymentStatus(getPaymentStatus(model.getResponseCode()));
        transaction.setResponseCode(model.getResponseCode());
        transaction.setResponseTime(LocalDateTime.now());
        transaction.setCallbackResponseData(JsonConverter.toJson(model));

        transactionRepo.save(transaction);
    }

    @Async
    public void ussdTransactionCallBackUpdate(String providerTransactionReference, USSDCallBackDto model) throws ServiceException {
        // find
        var ussdTranxOpt = transactionRepo.findByProviderTransactionReferenceAndPaymentChannel(providerTransactionReference, EPaymentChannel.USSD);
        if(ussdTranxOpt.isEmpty()){
            log.error(String.format("Transaction received with provider reference: %s which could not be found", providerTransactionReference));
            // perform other alerting functions.
            throw new ServiceException(String.format("Transaction not found by provider reference %s", providerTransactionReference));
        }

        var transaction = ussdTranxOpt.get();
        transaction.setPaymentStatus(getPaymentStatus(model.getResponseCode()));
        transaction.setResponseCode(model.getResponseCode());
        transaction.setResponseTime(LocalDateTime.now());
        transaction.setCallbackResponseData(JsonConverter.toJson(model));

        transactionRepo.save(transaction);
    }


    private PurchaseDto getResponse(TransactionRecord transactionRecord){
        return new PurchaseDto(transactionRecord);
    }

    private EPaymentStatus getPaymentStatus(String responseCode){
        if(StringUtils.isBlank(responseCode)){
            return EPaymentStatus.INCONCLUSIVE;
        } else if (!StringUtils.isBlank(webFailureCodes) && webFailureCodes.contains(responseCode)) {
            return  EPaymentStatus.FAILED;
        } else if (webTransferSuccessCode.equalsIgnoreCase(responseCode)) {
            return EPaymentStatus.SUCCESSFUL;
        }else {
            return EPaymentStatus.INCONCLUSIVE;
        }
    }

    private Page<PurchaseDto> getResponses(Page<TransactionRecord> transactionRecords){
        List<PurchaseDto> responses = new ArrayList<>();
        for (TransactionRecord transactionRecord: transactionRecords){
            responses.add(getResponse(transactionRecord));
        }

        return new PageImpl<>(responses, transactionRecords.getPageable(), transactionRecords.getTotalElements());
    }
}
