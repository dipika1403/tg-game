package com.galvanize.characterservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestParameterException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestParameterException.class);
    public BadRequestParameterException(String exception) {
        super(exception);
        LOGGER.warn("BadRequestParameterException: {}", exception);
    }
}
