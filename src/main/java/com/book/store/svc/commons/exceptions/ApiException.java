package com.book.store.svc.commons.exceptions;

import com.book.store.svc.commons.exceptions.ServiceException;
import lombok.Getter;

@Getter
public class ApiException extends ServiceException {
    public ApiException(int code, String message, Throwable ex) {
        super(code, message, ex);
    }

    public ApiException(int code, String message) {
        super(code, message);
    }

    public ApiException(String message) {
        super(message);
    }
}
