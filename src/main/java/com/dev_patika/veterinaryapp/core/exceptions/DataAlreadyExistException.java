package com.dev_patika.veterinaryapp.core.exceptions;

public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message, Throwable err) {
        super(message, err);
    }
}
