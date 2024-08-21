package com.example.demo.commons.exceptions;

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
