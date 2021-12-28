package com.projetdp.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleNotFound(Exception nfe) {
        //LOG error
        Error error = new Error(nfe.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
}