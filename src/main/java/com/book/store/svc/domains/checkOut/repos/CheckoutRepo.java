package com.book.store.svc.domains.checkOut.repos;

import com.book.store.svc.domains.checkOut.models.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckoutRepo extends JpaRepository<CheckOut, UUID> {

    Optional<CheckOut> findByCode(String code);

}
