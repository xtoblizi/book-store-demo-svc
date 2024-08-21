package com.example.demo.commons.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends ServiceValidationException{
    public NotFoundException(String message) {
        super(404, message);
    }
}
