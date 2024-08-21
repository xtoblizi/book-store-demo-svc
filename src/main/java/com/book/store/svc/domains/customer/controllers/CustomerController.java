package com.book.store.svc.domains.customer.controllers;

import com.book.store.svc.domains.customer.db.models.Customer;
import com.book.store.svc.domains.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Customer> findByCode( @RequestParam(name = "page", defaultValue = "0")int page,
                                      @RequestParam(name = "size", defaultValue = "20") int size) {
        return customerService.getAllCustomers(page, size);
    }
}
