package com.example.demo.commons.exceptions;

import lombok.Getter;

@Getter
public class ServiceException extends Exception{
    private int code;
    private String obj;
    public ServiceException(int code, String message, Throwable ex){
        super(message, ex);
        this.code = code;
    }

    public ServiceException(int code, String message){
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, String obj){
        super(message);
        this.code = code;
        this.obj = obj;
    }
    public ServiceException(String message){
        super(message);
        this.code = 500;
    }
}

