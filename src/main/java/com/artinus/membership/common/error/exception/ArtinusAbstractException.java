package com.artinus.membership.common.error.exception;

import com.artinus.membership.common.error.ArtinusErrorCode;
import org.springframework.http.HttpStatus;

abstract public class ArtinusAbstractException extends RuntimeException {
    public HttpStatus httpStatus;
    public ArtinusErrorCode errorCode;

    public ArtinusAbstractException(String message, HttpStatus httpStatus, ArtinusErrorCode errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
