package com.cannibal90.petclinic.WEB.exception;

import com.cannibal90.petclinic.WEB.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<?> handleCustomException(ApiException apiException) {
    HttpStatus status = apiException.getStatus();
    ExceptionDTO exceptionDTO =
        new ExceptionDTO(
            status.value(), status, apiException.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));

    return ResponseEntity.status(status).body(exceptionDTO);
  }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleCustomException(MethodArgumentNotValidException methodArgumentNotValidException) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message =  getAllValidationMessages(methodArgumentNotValidException);
        ExceptionDTO exceptionDTO =
                new ExceptionDTO(
                        status.value(), status, message, ZonedDateTime.now(ZoneId.of("Z")));

        return ResponseEntity.status(status).body(exceptionDTO);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException runtimeException) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDTO exceptionDTO =
                new ExceptionDTO(
                        status.value(), status, "Internal Server Error: " + runtimeException.getMessage(), ZonedDateTime.now(ZoneId.of("Z")));

        return ResponseEntity.status(status).body(exceptionDTO);
    }

    private String getAllValidationMessages(MethodArgumentNotValidException methodArgumentNotValidException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Validation Errors: ");
        methodArgumentNotValidException.getFieldErrors().forEach(fieldError -> {
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        });
        String message =  stringBuilder.toString();
        return  message.substring(0, message.length() - 2);
    }
}
