package com.cannibal90.petclinic.WEB.exception;

import org.springframework.http.HttpStatus;

public class WrongParameterException extends ApiException{
    public WrongParameterException(String message) {
        super(message);
    }

    public WrongParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
