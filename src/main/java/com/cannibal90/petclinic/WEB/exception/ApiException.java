package com.cannibal90.petclinic.WEB.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
