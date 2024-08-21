package com.book.store.svc.commons.exceptions;

import com.book.store.svc.commons.exceptions.ServiceValidationException;
import lombok.Getter;

@Getter
public class NotFoundException extends ServiceValidationException {
    public NotFoundException(String message) {
        super(404, message);
    }
}
