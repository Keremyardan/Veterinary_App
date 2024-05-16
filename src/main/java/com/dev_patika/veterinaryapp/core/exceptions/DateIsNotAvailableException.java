package com.dev_patika.veterinaryapp.core.exceptions;

public class DateIsNotAvailableException extends RuntimeException{
    public DateIsNotAvailableException(String message, Throwable err) {
        super(message, err);
    }
}
