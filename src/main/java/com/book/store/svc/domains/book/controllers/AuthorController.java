package com.book.store.svc.domains.book.controllers;

import com.book.store.svc.commons.exceptions.ServiceException;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author save(@Valid @RequestBody Author author) throws ServiceException {
        return authorService.save(author);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Page<Author> findAuthors(@RequestParam(name = "page", defaultValue = "0")int page,
                                    @RequestParam(name = "size", defaultValue = "20")int size) {
        return authorService.findAuthors(page, size);
    }

}
