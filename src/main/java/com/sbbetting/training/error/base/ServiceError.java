package com.sbbetting.training.error.base;

import org.springframework.http.HttpStatus;

public interface ServiceError {

    int getCode();

    String getMessage();

    HttpStatus getStatus();
}
