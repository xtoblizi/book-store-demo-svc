package com.book.store.svc.domains.book.db.repos;

import com.book.store.svc.domains.book.db.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepo extends JpaRepository<Author, UUID> {

    Optional<Author> findByCode(String code);
}
