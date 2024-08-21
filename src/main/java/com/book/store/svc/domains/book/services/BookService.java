package com.book.store.svc.domains.book.services;

import com.book.store.svc.commons.exceptions.ApiException;
import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.commons.utils.SortUtil;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.db.repos.BookRepo;
import com.book.store.svc.domains.book.dtos.BookRequest;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.services.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorService authorService;

    public Book save(BookRequest request) throws ServiceException {
        if(bookRepo.existsByIsbn(request.isbn())){
            throw new ApiException("Book already exist with this ISBN");
        }
        var author = authorService.findByCode(request.authorCode());

        var book = new Book(request.title(), request.isbn(), author, request.genre(),
                request.publicationDate());
        log.info(String.format("About to save new book with title: %s", request.title()));
        bookRepo.save(book);
        return book;
    }

    public Page<Book> findBooks(String title, String author, EGenre genre, LocalDate publishDate, int page, int size) {
        Pageable pageable = SortUtil.getSortedPageable(page, size, SortDirection.DESCENDING, "");
        title = StringUtils.isBlank(title)? null: title;
        author = StringUtils.isBlank(author)? null: author;
        log.info("About to retrieve list of books");
        return bookRepo.findBooks(title, author, genre, publishDate, pageable);
    }

    public Book findByCode(String code) throws NotFoundException {
        return bookRepo.findByCode(code).orElseThrow(()->
                new NotFoundException(String.format("No Book found by this code: %s", code)));
    }
}

