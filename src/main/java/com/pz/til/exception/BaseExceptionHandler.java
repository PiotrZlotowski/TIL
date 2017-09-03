package com.pz.til.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Log
public abstract class BaseExceptionHandler {

    private static final ExceptionMapping DEFAULT = new ExceptionMapping("SERVER_ERROR", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final Map<Class, ExceptionMapping> mappedExceptions = new HashMap<>();


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable throwable, HttpServletResponse response) {
        ExceptionMapping exceptionMapping = mappedExceptions.getOrDefault(throwable.getClass(), DEFAULT);
        response.setStatus(exceptionMapping.status.value());

        log.info(String.format("Handling error: %s, %s with code the %s", exceptionMapping.message, exceptionMapping.code, exceptionMapping.status));

        return new ErrorResponse(exceptionMapping.code, exceptionMapping.message);
    }

    protected void registerException(final Class<?> clazz, final String code, final String message, final HttpStatus httpStatus) {
        mappedExceptions.put(clazz, new ExceptionMapping(message, code, httpStatus));
    }


    @AllArgsConstructor
    private static class ExceptionMapping {
        private final String message;
        private final String code;
        private final HttpStatus status;
    }

    @Data
    private static class ErrorResponse {
        private final String code;
        private final String message;
    }


}
