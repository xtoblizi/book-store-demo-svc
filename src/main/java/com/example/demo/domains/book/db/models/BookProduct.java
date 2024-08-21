package com.example.demo.domains.book.db.models;

import com.example.demo.commons.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "book_products")
@Entity
public class BookProduct extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="book_code", nullable = false, referencedColumnName = "code")
    private Book book;
    @Column(nullable = false)
    private BigDecimal price;
    private String category;
    private BigDecimal discount;
    @Column(name = "target_country")
    private String note; // optional

    public BookProduct(Book book, BigDecimal price, String category, BigDecimal discount, String note) {
        this.book = book;
        this.price = price;
        this.category = category;
        this.discount = discount;
        this.note = note;
    }

    public BookProduct(){}
}
