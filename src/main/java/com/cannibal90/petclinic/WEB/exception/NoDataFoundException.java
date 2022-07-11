package com.cannibal90.petclinic.WEB.exception;

import org.springframework.http.HttpStatus;

public class NoDataFoundException extends ApiException{
    public NoDataFoundException(String message) {
        super(message);
    }
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
