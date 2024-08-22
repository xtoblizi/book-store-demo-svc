package com.book.store.svc.services;

import com.book.store.svc.BaseIntegrationTest;
import com.book.store.svc.commons.exceptions.ServiceValidationException;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.db.repos.AuthorRepo;
import com.book.store.svc.domains.book.dtos.BookRequest;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.services.BookProductService;
import com.book.store.svc.domains.book.services.BookService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class BookServiceTest extends BaseIntegrationTest {

    @Autowired
    protected BookService bookService;

    @Autowired
    protected AuthorRepo authorRepo;
    protected Author author = null;

    @Before
    public void init(){
        author = new Author();
        author.setFirstName("John");
        author.setLastName("Abayomi");
        author.setOtherInfo("Romance novel writer");
        authorRepo.save(author);
    }

    @Test
    @SneakyThrows
    public void create_book_should_save_successfully(){
        // arrange
        var bookRequest = new BookRequest("Yellow sun", "1454-45", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));

        // act
        var book = bookService.save(bookRequest);

        // assert
        Assert.assertNotNull(book);
        Assert.assertEquals(book.getTitle(), bookRequest.title());
    }

    @Test(expected = ServiceValidationException.class)
    @SneakyThrows
    public void create_book_test_should_throw_not_null_validation_exception(){
        // arrange
        var bookRequest = new BookRequest("", "", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));

        // act and assert
        var book = bookService.save(bookRequest);
        Assert.assertNull(book);

    }

    @SneakyThrows
    @Test
    public void findBooks_should_filter_and_return_1_book(){
        // arrange
        var bookRequest = new BookRequest("Yellow sun", "1454-45", author.getCode(), EGenre.Poetry, LocalDate.now().minusYears(5));
        var book = bookService.save(bookRequest);

        //act
        var books = bookService.findBooks("Yellow sun", null, null, null, 0, 10);

        // assert
        Assert.assertNotNull(books);
        Assert.assertEquals(books.stream().count(), 1);
    }
}
