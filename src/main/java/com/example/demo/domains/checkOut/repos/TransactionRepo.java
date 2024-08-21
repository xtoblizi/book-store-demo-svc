package com.example.demo.domains.checkOut.repos;

import com.example.demo.domains.checkOut.enums.EPaymentChannel;
import com.example.demo.domains.checkOut.models.TransactionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionRecord, UUID> {
    Optional<TransactionRecord> findByCode(String code);
    @Query("Select t from TransactionRecord  t WHERE t.customer.code=:customerCode")
    Page<TransactionRecord> findByCustomerInfo(@Param("customerCode") String customerCode, Pageable pageable);

    Optional<TransactionRecord> findByProviderTransactionReferenceAndPaymentChannel(String reference, EPaymentChannel channel);

}
