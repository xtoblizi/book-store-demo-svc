package com.example.demo.domains.book.dtos;

import com.example.demo.domains.book.enums.EGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookRequest(@NotBlank(message = "Kindly provide a book title") String title,
                          @NotBlank(message = "kindly provide books ISBN") String isbn,
                          @NotBlank(message = "Kindly provide author's info") String authorCode,
                          @NotNull(message = "Kindly provide a genre") EGenre genre,
                          @NotNull(message = "Kindly provide publish date") LocalDate publicationDate) { }
