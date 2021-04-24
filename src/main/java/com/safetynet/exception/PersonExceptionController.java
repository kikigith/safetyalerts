package com.safetynet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PersonExceptionController {

    @ExceptionHandler(PersonInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlePersonInvalidException(PersonInvalidException pie){
        return new ResponseEntity<Object>("Invalid Person :"+pie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException pie){
        return new ResponseEntity<Object>("Person not found :"+pie.getMessage(),HttpStatus.NOT_FOUND);
    }
}
