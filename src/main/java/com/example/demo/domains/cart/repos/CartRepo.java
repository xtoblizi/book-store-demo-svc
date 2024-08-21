package com.example.demo.domains.cart.repos;

import com.example.demo.domains.cart.db.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCode(String code);
    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems WHERE c.code=:code ")
    Optional<Cart> findByCodeIncludeItems(@Param("code") String code);
    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems" +
            " WHERE c.customer.code=:customerCode AND c.isCheckedOut=false")
    Optional<Cart> findPendingCartByCustomerAndIncludeItems(@Param("customerCode") String customerCode);
}
