package com.book.store.svc.services;

import com.book.store.svc.BaseIntegrationTest;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.db.repos.AuthorRepo;
import com.book.store.svc.domains.book.dtos.BookRequest;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.services.BookService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class BookServiceTest extends BaseIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepo authorRepo;
    private Author author = null;

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
}
