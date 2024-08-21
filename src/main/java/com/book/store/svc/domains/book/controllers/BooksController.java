package com.book.store.svc.domains.book.controllers;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.db.models.BookProduct;
import com.book.store.svc.domains.book.dtos.BookProductRequest;
import com.book.store.svc.domains.book.dtos.BookRequest;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.services.BookProductService;
import com.book.store.svc.domains.book.services.BookService;
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

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookProduct> findProducts(@RequestParam(name = "page", defaultValue = "0")int page,
                                   @RequestParam(name = "size", defaultValue = "20") int size) {
        return bookProductService.findAllBookProducts(page, size);
    }

    // Make a book a product. One book can exist as multiple products.
    // The product is what customer will purchase.
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public BookProduct makeBookProduct(@Valid @RequestBody BookProductRequest request) throws ServiceException {
        return bookProductService.createProduct(request);
    }
}
