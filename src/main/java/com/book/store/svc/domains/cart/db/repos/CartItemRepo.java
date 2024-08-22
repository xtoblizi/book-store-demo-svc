package com.book.store.svc.domains.cart.db.repos;

import com.book.store.svc.domains.cart.db.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, UUID> {

    Optional<CartItem> findByCode(String code);
    Set<CartItem> findAllByCart_Code(String cartCode);
}
