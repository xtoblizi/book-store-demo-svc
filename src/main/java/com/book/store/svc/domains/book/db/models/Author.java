package com.book.store.svc.domains.book.db.models;

import com.book.store.svc.commons.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "authors")
@Entity
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    private String otherInfo;
}
