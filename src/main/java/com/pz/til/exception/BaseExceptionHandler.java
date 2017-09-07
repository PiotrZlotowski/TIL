package com.pz.til.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log
public abstract class BaseExceptionHandler {

    private static final ExceptionMapping DEFAULT = new ExceptionMapping("SERVER_ERROR", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final Map<Class, ExceptionMapping> mappedExceptions = new HashMap<>();

    public BaseExceptionHandler() {
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Set<FieldErrorResponse> fieldsErrors = fieldErrors.stream().map(fe -> new FieldErrorResponse(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toSet());
        return new ErrorResponse("Bad Request", "Validation failed. Fields rejected", fieldsErrors);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable throwable, HttpServletResponse response) {
        ExceptionMapping exceptionMapping = mappedExceptions.getOrDefault(throwable.getClass(), DEFAULT);
        response.setStatus(exceptionMapping.status.value());
        log.info(String.format("Handling error: %s, %s with reason the %s", exceptionMapping.message, exceptionMapping.reason, exceptionMapping.status));
        return new ErrorResponse(exceptionMapping.reason, exceptionMapping.message, Collections.emptySet());
    }

    protected void registerException(final Class<?> clazz, final String reason, final String message, final HttpStatus httpStatus) {
        mappedExceptions.put(clazz, new ExceptionMapping(message, reason, httpStatus));
    }


    @AllArgsConstructor
    private static class ExceptionMapping {
        private final String message;
        private final String reason;
        private final HttpStatus status;
    }

    @Data
    private static class ErrorResponse {
        private final String code;
        private final String message;
        private final Set<FieldErrorResponse> fieldsErrors;
    }

    @Data
    private static class FieldErrorResponse {
        private final String name;
        private final String errorMessage;
    }


}
