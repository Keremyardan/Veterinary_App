package com.dev_patika.veterinaryapp.core.config.result;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result { // this class is a generic class for Result objects.

    private T data;

    public ResultData(boolean status, String message, String httpCode, T data) {
        super(status, message, httpCode);
        this.data = data;
    }

    public boolean isSuccess() {
        return super.isStatus();
    }
}
