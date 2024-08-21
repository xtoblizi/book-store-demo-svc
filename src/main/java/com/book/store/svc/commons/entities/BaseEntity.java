package com.book.store.svc.commons.entities;

import com.book.store.svc.commons.converters.LocalDateTimeConverter;
import com.book.store.svc.commons.utils.DateTimeUtil;
import com.book.store.svc.commons.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String code;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = DateTimeUtil.getCurrentLocalDateTime();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = DateTimeUtil.getCurrentLocalDateTime();
    }

    public static String generateCode(Integer maxCodeSize) {
        if (maxCodeSize == null || maxCodeSize < 1)
            maxCodeSize = 8;

        if (maxCodeSize > 16)
            throw new RuntimeException("Exceeded 16 bit UUID code generation size");

        return IdGenerator.getUuidBasedId().substring(0, maxCodeSize);
    }
}
