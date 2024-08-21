package com.book.store.svc.commons.exceptions;

import com.book.store.svc.commons.exceptions.ServiceException;
import lombok.Getter;

@Getter
public class ServiceValidationException extends ServiceException {
    public ServiceValidationException(int code, String message, Throwable ex) {
        super(code, message, ex);
    }

    public ServiceValidationException(int code, String message) {
        super(code, message);
    }

    public ServiceValidationException(String message) {
        super(400, message);
    }

    public ServiceValidationException(int code, String message, String obj){
        super(code, message, obj);
    }
}

