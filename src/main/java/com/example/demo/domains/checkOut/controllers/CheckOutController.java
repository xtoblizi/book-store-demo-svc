package com.example.demo.domains.checkOut.controllers;

import com.example.demo.commons.exceptions.NotFoundException;
import com.example.demo.domains.checkOut.dtos.CheckOutRequest;
import com.example.demo.domains.checkOut.dtos.callbacks.WebTransactionCallbackDto;
import com.example.demo.domains.checkOut.enums.EPaymentChannel;
import com.example.demo.domains.checkOut.models.CheckOut;
import com.example.demo.domains.checkOut.services.CheckoutService;
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
