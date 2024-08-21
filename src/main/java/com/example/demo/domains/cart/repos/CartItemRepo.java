package com.example.demo.domains.cart.repos;

import com.example.demo.domains.cart.db.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, UUID> {

    Optional<CartItem> findByCode(String code);
}
