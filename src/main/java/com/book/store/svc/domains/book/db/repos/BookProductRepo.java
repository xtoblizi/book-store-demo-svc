package com.book.store.svc.domains.book.db.repos;

import com.book.store.svc.domains.book.db.models.BookProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookProductRepo extends JpaRepository<BookProduct, UUID> {

    @Query("select bP from BookProduct bP where bP.book.code=:bookCode " +
            "AND :category is null or bP.category=:category")
    Optional<BookProduct> findByBookAndCategory(@Param("bookCode") String bookCode,
                                                @Param("category")String category);

    Optional<BookProduct> findByCode(String code);
}
