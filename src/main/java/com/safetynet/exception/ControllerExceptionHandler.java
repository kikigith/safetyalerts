package com.safetynet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonInvalidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlePersonInvalidException(PersonInvalidException pie, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Invalid person" +pie.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException pie){
        return new ResponseEntity<Object>("Person not found :"+pie.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FirestationInvalidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleFirestationInvalidException(FirestationInvalidException pie, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Invalid person" +pie.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FirestationNotFoundException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleFirestationNotFoundException(FirestationNotFoundException pie, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Person not found" +pie.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicalRecordInvalidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMedicalRecordInvalidException(MedicalRecordInvalidException pie, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Invalid person" +pie.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMedicalRecordNotFoundException(MedicalRecordNotFoundException pie, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Person not found" +pie.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }*/

    /*@Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }*/
}
