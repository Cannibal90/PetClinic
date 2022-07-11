package com.cannibal90.petclinic.WEB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ExceptionDTO {

    private int code;
    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timestamp;
}
