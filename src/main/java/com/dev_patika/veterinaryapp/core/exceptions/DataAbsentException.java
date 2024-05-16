package com.dev_patika.veterinaryapp.core.exceptions;

public class DataAbsentException extends RuntimeException{
    public DataAbsentException(String message, Throwable err) {
        super(message, err);
    }
}
