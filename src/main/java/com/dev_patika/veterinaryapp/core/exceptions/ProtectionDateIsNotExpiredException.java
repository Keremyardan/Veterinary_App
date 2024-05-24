package com.dev_patika.veterinaryapp.core.exceptions;

public class ProtectionDateIsNotExpiredException extends RuntimeException{
    public ProtectionDateIsNotExpiredException(String message) {
        super(message);
    }
}
