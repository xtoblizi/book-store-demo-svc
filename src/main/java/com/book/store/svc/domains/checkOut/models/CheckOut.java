package com.book.store.svc.domains.checkOut.models;

import com.book.store.svc.commons.entities.BaseEntity;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.checkOut.enums.ECheckOutStatus;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "check_outs")
@Entity
@NoArgsConstructor
public class CheckOut extends BaseEntity {
    @JoinColumn(name = "cart_code", referencedColumnName = "code", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;
    @Enumerated(EnumType.STRING)
    private EPaymentChannel paymentChannel;
    @Enumerated(EnumType.STRING)
    private ECheckOutStatus checkOutStatus;

    public CheckOut(Cart cart, EPaymentChannel paymentChannel){
        this.cart = cart;
        this.paymentChannel = paymentChannel;
        this.checkOutStatus = ECheckOutStatus.PENDING;
    }
}

