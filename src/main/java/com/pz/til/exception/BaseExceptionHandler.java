package com.pz.til.exception;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {


}
