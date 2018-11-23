package com.galvanize.itemservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectNotFoundException.class);
    public ObjectNotFoundException(String exception) {
        super(exception);
        LOGGER.warn("ObjectNotFoundException: {}", exception);
    }
}
