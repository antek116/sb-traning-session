package com.sbbetting.training.error;

import com.sbbetting.training.error.base.ServiceError;
import org.springframework.http.HttpStatus;

public enum TokenError implements ServiceError {
    INVALID_TOKEN_ERROR(Messages.INVALID_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_FORMAT_ERROR(Messages.INVALID_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_EXPIRED_ERROR(Messages.ACCESS_TOKEN_HAS_EXPIRED, HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED_ERROR(Messages.REFRESH_TOKEN_HAS_EXPIRED, HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN_ERROR(Messages.INVALID_REFRESH_TOKEN, HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_NOT_FOUND_ERROR(Messages.ACCESS_TOKEN_NOT_FOUND, HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    TokenError(String message, HttpStatus status) {
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

    private interface Messages {
        String ACCESS_TOKEN_NOT_FOUND = "No access token provided.";
        String INVALID_ACCESS_TOKEN = "Access token is invalid.";
        String ACCESS_TOKEN_HAS_EXPIRED = "Access token has expired.";
        String REFRESH_TOKEN_HAS_EXPIRED = "Refresh token has expired.";
        String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    }

}
