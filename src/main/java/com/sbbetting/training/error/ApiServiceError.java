package com.sbbetting.training.error;

import com.sbbetting.training.error.base.ServiceError;
import org.springframework.http.HttpStatus;

public enum ApiServiceError implements ServiceError {

    INTERNAL_SERVICE_ERROR(Message.INTERNAL_SERVICE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ApiServiceError(String message, HttpStatus status) {
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
        String INTERNAL_SERVICE_ERROR = "Internal service error. Please contact your api administrator.";
    }
}
