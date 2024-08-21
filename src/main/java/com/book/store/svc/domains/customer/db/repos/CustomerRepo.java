package com.book.store.svc.domains.customer.db.repos;

import com.book.store.svc.domains.customer.db.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmailIgnoreCase(String email);
    Optional<Customer> findByCode(String code);
}
