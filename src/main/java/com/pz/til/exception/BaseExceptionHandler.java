package com.pz.til.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ExceptionMapping DEFAULT = new ExceptionMapping("SERVER_ERROR", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final Map<Class, ExceptionMapping> mappedExceptions = new HashMap<>();

//    @ResponseStatus(BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//        Set<FieldErrorResponse> fieldsErrors = fieldErrors.stream().map(fe -> new FieldErrorResponse(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toSet());
//        return new ErrorResponse("Bad Request", "Validation failed. Fields rejected", fieldsErrors);
//    }

//    @ExceptionHandler(Throwable.class)
//    @ResponseBody
//    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable throwable) {
//        log.info("Incoming original exception: " + throwable.getMessage());
//        ExceptionMapping exceptionMapping = mappedExceptions.getOrDefault(throwable.getClass(), DEFAULT);
//        log.info(String.format("Handling error: %s, %s with reason the %s", exceptionMapping.message, exceptionMapping.reason, exceptionMapping.status));
//        ErrorResponse errorResponse = new ErrorResponse(exceptionMapping.reason, exceptionMapping.message, Collections.emptySet());
//        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(exceptionMapping.status.value()));
//    }

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
