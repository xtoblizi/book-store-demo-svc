package com.book.store.svc.domains.book.services;

import com.book.store.svc.commons.exceptions.NotFoundException;
import com.book.store.svc.commons.utils.SortUtil;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.db.repos.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
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

    public Page<Author> findAuthors (int page,int size){
        return authorRepo.findAll(SortUtil.getSortedPageable(page, size, SortDirection.DESCENDING, ""));
    }

    public Author save(Author author){
       return authorRepo.save(author);
    }
}
