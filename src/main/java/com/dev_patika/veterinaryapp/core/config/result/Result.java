package com.dev_patika.veterinaryapp.core.config.result;

import lombok.Getter;

@Getter
public class Result { // this class is a generic class for Result objects.
    private boolean status;
    private String message;
    private String httpCode;

    public Result(boolean status, String message, String httpCode) {
        this.status = status;
        this.message = message;
        this.httpCode = httpCode;
    }



}
