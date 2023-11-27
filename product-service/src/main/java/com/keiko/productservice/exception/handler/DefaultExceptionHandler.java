package com.keiko.productservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> exception (Exception ex) {
        final String message = String.format (
                "Exception: %s,\nreason: %s", ex.getClass (), ex.getMessage ());
        return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).body (message);
    }
}
