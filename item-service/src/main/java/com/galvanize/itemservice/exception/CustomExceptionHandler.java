package com.galvanize.itemservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<CustomException> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.NOT_FOUND.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class CustomException {
        private int error_code;
        private String message;
        private String uri;
    }
}

