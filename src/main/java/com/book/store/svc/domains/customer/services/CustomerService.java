package com.book.store.svc.domains.customer.services;


import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.utils.SortUtil;
import com.book.store.svc.domains.customer.db.models.Customer;
import com.book.store.svc.domains.customer.db.repos.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    public void save(Customer customer){
        customerRepo.save(customer);
    }

    public void save(List<Customer> customers){
        customerRepo.saveAll(customers);
    }

    public Optional<Customer> findByEmail(String email){
        return customerRepo.findByEmailIgnoreCase(email);
    }

    public Customer findByCode(String code) throws NotFoundException {
        return customerRepo.findByCode(code).orElseThrow(()->
                new NotFoundException(String.format("Customer not found by code: %s", code)));
    }

    public Page<Customer> getAllCustomers(int page, int size){
        var pageable = SortUtil.getSortedPageable(page, size, SortDirection.DESCENDING, "");
        return customerRepo.findAll(pageable);
    }
}
