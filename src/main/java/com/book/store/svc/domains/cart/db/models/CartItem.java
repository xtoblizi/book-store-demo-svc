package com.book.store.svc.domains.cart.db.models;

import com.book.store.svc.commons.entities.BaseEntity;
import com.book.store.svc.domains.book.db.models.BookProduct;
import com.book.store.svc.domains.cart.db.models.Cart;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@Table(name = "cart_items")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class CartItem extends BaseEntity {
    
    @JoinColumn(name = "product", nullable = false, referencedColumnName = "code")
    @ManyToOne(fetch = FetchType.EAGER)
    private BookProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "cart_code", referencedColumnName = "code")
    private Cart cart;
    
    @Column(nullable = false)
    private Integer quantity = 1;

    public CartItem(BookProduct product, Cart cart, Integer quantity) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CartItem cartItem)) return false;

        return new EqualsBuilder().append(getCode(), cartItem.getCode()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getCode()).toHashCode();
    }
}
