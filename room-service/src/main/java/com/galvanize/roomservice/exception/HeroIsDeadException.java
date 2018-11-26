package com.galvanize.roomservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class HeroIsDeadException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeroIsDeadException.class);
    public HeroIsDeadException(String exception) {
        super(exception);
        LOGGER.warn("HeroIsDeadException: {}", exception);
    }
}
