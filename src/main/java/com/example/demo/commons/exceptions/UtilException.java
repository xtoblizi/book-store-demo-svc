package com.example.demo.commons.exceptions;

public class UtilException extends Exception{
    public UtilException(String message)  {
        super(message);
    }

    public UtilException(String message, Throwable throwable)  {
        super(message, throwable);
    }
}
