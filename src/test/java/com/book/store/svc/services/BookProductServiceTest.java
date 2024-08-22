package com.book.store.svc.services;

import com.book.store.svc.commons.exceptions.ServiceValidationException;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.dtos.BookProductRequest;
import com.book.store.svc.domains.book.dtos.BookRequest;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.services.BookProductService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookProductServiceTest extends BookServiceTest {

    @Autowired
    private BookProductService bookProductService;

    private Book firstBook = null;

    @SneakyThrows
    @Override
    public void init(){
        super.init();
        var bookRequest = new BookRequest("Green sun", "1454-47", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));
        firstBook = bookService.save(bookRequest);
    }

    @Test
    @SneakyThrows
    public void create_product_test_should_succeed_when_data_valid(){
        //arrange
        var bookRequest = new BookRequest("Yellow sun", "1454-45", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));
        var book = bookService.save(bookRequest);
        BookProductRequest request = new BookProductRequest();
        request.setBookCode(book.getCode());
        request.setPrice(BigDecimal.valueOf(1300));
        request.setDiscount(BigDecimal.valueOf(10));

        // act
        var bookProduct = bookProductService.createProduct(request);

        // assert
        Assert.assertNotNull(bookProduct);
        Assert.assertEquals(request.getPrice(), bookProduct.getPrice());
    }

    @Test(expected = ServiceValidationException.class)
    @SneakyThrows
    public void create_product_test_should_throw_exception_when_product_already_exist(){
        //arrange
        var bookRequest = new BookRequest("Yellow sun", "1454-45", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));
        var book = bookService.save(bookRequest);
        var oldProduct = new BookProductRequest();
        oldProduct.setBookCode(book.getCode());
        oldProduct.setPrice(BigDecimal.valueOf(1300));
        oldProduct.setDiscount(BigDecimal.valueOf(10));
        bookProductService.createProduct(oldProduct);

        // act
        var newProductRequest = new BookProductRequest(book.getCode(), "",
                BigDecimal.ZERO, null, BigDecimal.valueOf(40000));
        var newProduct = bookProductService.createProduct(newProductRequest);

        // assert
        Assert.assertNull(newProduct);
    }

    @SneakyThrows
    @Test
    public void findBookProducts_should_return_value(){
        // arrange
        var newProductRequest = new BookProductRequest(firstBook.getCode(), "",
                BigDecimal.ZERO, null, BigDecimal.valueOf(40000));
        var newProduct = bookProductService.createProduct(newProductRequest);

        //act
        var bookProducts = bookProductService.findAllBookProducts(0, 10);

        // assert
        Assert.assertNotNull(bookProducts);
        Assert.assertEquals(bookProducts.stream().count(), 1);
    }
}
