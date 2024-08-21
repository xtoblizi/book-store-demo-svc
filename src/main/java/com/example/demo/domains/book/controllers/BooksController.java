package com.example.demo.domains.book.controllers;

import com.example.demo.commons.exceptions.NotFoundException;
import com.example.demo.commons.exceptions.ServiceException;
import com.example.demo.domains.book.db.models.Book;
import com.example.demo.domains.book.db.models.BookProduct;
import com.example.demo.domains.book.dtos.BookProductRequest;
import com.example.demo.domains.book.dtos.BookRequest;
import com.example.demo.domains.book.enums.EGenre;
import com.example.demo.domains.book.services.BookProductService;
import com.example.demo.domains.book.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BooksController {

    private final BookService bookService;
    private final BookProductService bookProductService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@Valid @RequestBody BookRequest bookRequest) throws ServiceException {
       return bookService.save(bookRequest);
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Book findByCode(@PathVariable(name = "code") String code) throws NotFoundException {
        return bookService.findByCode(code);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Book> findBooks(@RequestParam(name = "title", required = false)String title,
                                @RequestParam(name = "author", required = false)String author,
                                @RequestParam(name = "genre", required = false) EGenre genre,
                                @RequestParam(name = "publishDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate publishDate,
                                @RequestParam(name = "page", defaultValue = "0")int page,
                                @RequestParam(name = "size", defaultValue = "20") int size) {
        return bookService.findBooks(title, author, genre, publishDate, page, size);
    }

    // Make a book a product.
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public BookProduct makeBookProduct(@Valid @RequestBody BookProductRequest request) throws ServiceException {
        return bookProductService.save(request);
    }
}
