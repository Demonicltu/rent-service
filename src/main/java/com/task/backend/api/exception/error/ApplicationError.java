package com.task.backend.api.exception.error;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ApplicationError {

    SYS_ERR("System error", INTERNAL_SERVER_ERROR),
    PRODUCT_NOT_FOUND("Product does not exist", NOT_FOUND),
    INVALID_COMMITMENT_PLAN("Commitment plan does not exist", NOT_FOUND);

    private final String errorName;
    private final String message;
    private final HttpStatus httpStatus;

    ApplicationError(String message, HttpStatus httpStatus) {
        this.errorName = this.name();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
