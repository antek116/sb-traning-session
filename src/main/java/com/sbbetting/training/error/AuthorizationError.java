package com.sbbetting.training.error;

import com.sbbetting.training.error.base.ServiceError;
import org.springframework.http.HttpStatus;

public enum AuthorizationError implements ServiceError {

    ACCESS_DENIED(Message.ACCESS_DENIED, HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;

    AuthorizationError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public int getCode() {
        return status.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    private interface Message {
        String ACCESS_DENIED = "Access Denied.";
    }
}
