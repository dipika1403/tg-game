package com.galvanize.roomservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class MoveForbiddenException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveForbiddenException.class);
    public MoveForbiddenException(String exception) {
        super(exception);
        LOGGER.warn("MoveForbiddenException: {}", exception);
    }
}
