package com.galvanize.roomservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomNotFoundException.class);
    public RoomNotFoundException(String exception) {
        super(exception);
        LOGGER.warn("RoomNotFoundException: {}", exception);
    }
}
