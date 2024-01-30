package com.keiko.notificationservice.exception;

import com.keiko.notificationservice.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exception (Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder ()
                .errorClass (ex.getClass ().toString ())
                .errorMessage (ex.getMessage ())
                .build ();
        return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).body (errorResponse);
    }
}
