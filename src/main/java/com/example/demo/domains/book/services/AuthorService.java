package com.example.demo.domains.book.services;

import com.example.demo.commons.exceptions.NotFoundException;
import com.example.demo.domains.book.db.models.Author;
import com.example.demo.domains.book.db.repos.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;

    public Author findByCode(String authorCode) throws NotFoundException {
        return authorRepo.findByCode(authorCode).orElseThrow(()->
                new NotFoundException(String.format("No author found by code: %s", authorCode))
        );
    }

    public void save(Author author){
        // validate that first nane amd last name exist
        authorRepo.save(author);
    }
}
