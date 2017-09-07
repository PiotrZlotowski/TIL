package com.pz.til.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class IllegalArgumentHandler extends BaseExceptionHandler {

    public IllegalArgumentHandler() {
        registerException(IllegalArgumentException.class, "Bad Request", "Invalid Argument", HttpStatus.BAD_REQUEST);
    }
}
