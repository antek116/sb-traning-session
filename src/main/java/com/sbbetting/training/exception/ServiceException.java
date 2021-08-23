package com.sbbetting.training.exception;

import com.sbbetting.training.error.base.ServiceError;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private final int code;
    private final String message;
    private final long timestamp;
    private final HttpStatus status;

    public ServiceException(ServiceError error) {
        this.code = error.getCode();
        this.message = error.getMessage();
        this.status = error.getStatus();
        this.timestamp = System.currentTimeMillis();
    }

    public ServiceException(String message, HttpStatus status) {
        this.code = status.value();
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
