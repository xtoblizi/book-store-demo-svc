package com.example.demo.bootstrap;

import com.example.demo.domains.customer.db.models.Customer;
import com.example.demo.domains.customer.services.CustomerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapService {

    private final CustomerService customerService;

    @PostConstruct
    public void SeeCustomers(){
        List<Customer> customers = List.of(
                new Customer("Abayomi", "Emeka", "a.emeka@switch.com", "32 Walter Carrignton Close, Enugu, Nigeria"),
                new Customer("John", "Sams", "j.sams@interswitch.com", "42 Red Street Close, Lagos, Nigeria"),
                new Customer("Chris", "Brown", "c.brown@switch.com", "32 Blue, Delta, Nigeria")
        );
        customerService.save(customers);
    }
}
