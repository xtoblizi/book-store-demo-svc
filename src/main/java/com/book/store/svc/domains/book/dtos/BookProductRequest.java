package com.book.store.svc.domains.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookProductRequest {
    @NotBlank(message = "Kindly provide book code")
    private String bookCode;
    private String note;
    /**
     * This can be used to further categorize books into product category
     * E.g Same book is being sold N20 as flash sales or less in combo category but slightly higher as single or premium
     */
    private BigDecimal discount;
    private String category;
    @NotNull(message = "Kindly provide the price of the this book as a product")
    private BigDecimal price;

}
