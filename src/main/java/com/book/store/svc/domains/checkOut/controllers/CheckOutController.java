package com.book.store.svc.domains.checkOut.controllers;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.domains.checkOut.dtos.CheckOutRequest;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.models.CheckOut;
import com.book.store.svc.domains.checkOut.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/check-out")
public class CheckOutController {

    private final CheckoutService checkoutService;

    @PostMapping("/{customerCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckOut checkOut(@PathVariable(name = "customerCode") String customerCode,
                             @Valid @RequestBody CheckOutRequest request) throws NotFoundException {

        return checkoutService.createCheckout(customerCode, request);
    }

    @GetMapping("/channels")
    @ResponseStatus(HttpStatus.OK)
    public List<EPaymentChannel> getPaymentChannels(){
        return checkoutService.getPaymentChannels();
    }
}
