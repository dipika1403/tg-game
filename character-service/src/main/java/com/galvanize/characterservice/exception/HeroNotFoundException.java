package com.galvanize.characterservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeroNotFoundException.class);
    public HeroNotFoundException(String exception) {
        super(exception);
        LOGGER.warn("HeroNotFoundException: {}", exception);
    }
}
