package com.book.store.svc.domains.book.db.repos;

import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.db.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepo extends JpaRepository<Book, UUID> {

    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE " +
            "(:title is null OR lower(b.title) LIKE lower(concat('%', concat(:name, '%')))) " +
            "AND (:author is null OR ((lower(b.author.firstName) LIKE lower(concat('%', concat(:author, '%'))))) OR  (lower(b.author.firstName) LIKE lower(concat('%', concat(:author, '%'))))) " +
            "AND (:genre is null OR b.genre=:genre ) " +
            "AND (:publishDate is null OR (b.publicationDate >=:publishDate AND  b.publicationDate <=:publishDate))")
    Page<Book> findBooks(@Param("title") String title,
                         @Param("author") String author,
                         @Param("genre") EGenre genre,
                         @Param("publishDate") LocalDate publishDate,
                         Pageable pageable);

    Optional<Book> findByCode(String code);
}
