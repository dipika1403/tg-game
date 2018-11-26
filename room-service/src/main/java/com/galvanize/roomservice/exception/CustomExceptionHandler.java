package com.galvanize.roomservice.exception;

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

    @ExceptionHandler(RoomNotFoundException.class)
    protected ResponseEntity<CustomException> handleRoomNotFoundException(RoomNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.NOT_FOUND.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MoveForbiddenException.class)
    protected ResponseEntity<CustomException> handleMoveForbiddenException(MoveForbiddenException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.FORBIDDEN.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HeroIsDeadException.class)
    protected ResponseEntity<CustomException> handleHeroIsDeadException(HeroIsDeadException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomException(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getLocalizedMessage(),
                request.getDescription(false)), HttpStatus.UNAUTHORIZED);
    }

    @Data
    @AllArgsConstructor
    private static class CustomException {
        private int error_code;
        private String message;
        private String uri;
    }
}

