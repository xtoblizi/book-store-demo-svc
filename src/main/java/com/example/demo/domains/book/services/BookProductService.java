package com.example.demo.domains.book.services;

import com.example.demo.commons.exceptions.NotFoundException;
import com.example.demo.commons.exceptions.ServiceException;
import com.example.demo.commons.exceptions.ServiceValidationException;
import com.example.demo.domains.book.db.models.BookProduct;
import com.example.demo.domains.book.db.repos.BookProductRepo;
import com.example.demo.domains.book.dtos.BookProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookProductService{
    private final BookService bookService;
    private final BookProductRepo bookProductRepo;
    
    public BookProduct save (BookProductRequest request) throws ServiceException {
        var book = bookService.findByCode(request.getBookCode());
        var existingProduct = bookProductRepo.findByBookAndCategory(request.getBookCode(), request.getCategory());
        if(Objects.nonNull(existingProduct)){
            throw new ServiceValidationException("Book already exist as a product with these info {bookCode and category}");
        }
        BookProduct bookProduct = new BookProduct(book, request.getPrice(),
                request.getCategory(), request.getDiscount(), request.getNote());
        bookProductRepo.save(bookProduct);

        log.info(String.format("New book product created with book code: %s", request.getBookCode()));
        return bookProduct;
    }

    public BookProduct findByCode(String code) throws NotFoundException {
        return bookProductRepo.findByCode(code).orElseThrow(()->
                new NotFoundException(String.format("No book product can be found by code: %s", code)));
    }

}
