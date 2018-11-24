package com.galvanize.characterservice.exception;

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

    @ExceptionHandler(HeroNotFoundException.class)
    protected ResponseEntity<CustomException> handleHeroNotFoundException(HeroNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.NOT_FOUND.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestParameterException.class)
    protected ResponseEntity<CustomException> handleBadRequestParameterException(BadRequestParameterException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class CustomException {
        private int error_code;
        private String message;
        private String uri;
    }
}

