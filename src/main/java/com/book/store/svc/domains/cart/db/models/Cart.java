package com.book.store.svc.domains.cart.db.models;

import com.book.store.svc.commons.entities.BaseEntity;
import com.book.store.svc.domains.cart.db.models.CartItem;
import com.book.store.svc.domains.customer.db.models.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "cart")
@Entity
@NoArgsConstructor
public class Cart extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_code", nullable = false, referencedColumnName = "code")
    private Customer customer;

    private BigDecimal grossTotal; // total without discount
    private BigDecimal totalDiscount;
    private BigDecimal total; // grossTotal - discount
    private boolean isCheckedOut;

    @OneToMany(mappedBy="cart", fetch = FetchType.LAZY)
    public Set<CartItem> cartItems = new HashSet<>();

    public Cart(Customer customer, BigDecimal grossTotal, BigDecimal totalDiscount){
        this.customer = customer;
        this.grossTotal = grossTotal;
        this.totalDiscount = totalDiscount;
        this.total = grossTotal.subtract(totalDiscount);
        this.isCheckedOut = false;
    }

    public Cart(Customer customer){
        this.customer = customer;
        this.grossTotal = BigDecimal.ZERO;
        this.totalDiscount = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.isCheckedOut = false;
    }

}

