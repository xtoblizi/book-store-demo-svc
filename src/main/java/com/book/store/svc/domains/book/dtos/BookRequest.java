package com.book.store.svc.domains.book.dtos;

import com.book.store.svc.domains.book.enums.EGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookRequest(@NotBlank(message = "Kindly provide a book title") String title,
                          @Pattern(regexp = "^[0-9]*$") @NotBlank(message = "kindly provide books ISBN") String isbn,
                          @NotBlank(message = "Kindly provide author's info") String authorCode,
                          @NotNull(message = "Kindly provide a genre") EGenre genre,
                          @NotNull(message = "Kindly provide publish date") LocalDate publicationDate) { }
