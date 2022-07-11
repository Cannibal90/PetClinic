package com.cannibal90.petclinic.WEB.exception;

public class WrongParameterException extends RuntimeException{
    public WrongParameterException(String message) {
        super(message);
    }
}
