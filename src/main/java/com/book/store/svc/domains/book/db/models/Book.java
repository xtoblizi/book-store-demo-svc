package com.book.store.svc.domains.book.db.models;

import com.book.store.svc.commons.converters.LocalDateTimeConverter;
import com.book.store.svc.commons.entities.BaseEntity;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.enums.EGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "books")
@Entity
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String isbn; // ISBN

    @Column(nullable = false)
    private EGenre genre;

    @JoinColumn(name = "author_code", referencedColumnName = "code", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDate publicationDate;

    public Book(String title, String isbn, Author author,
                EGenre genre, LocalDate publicationDate) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
    }

    public Book(){}
}

