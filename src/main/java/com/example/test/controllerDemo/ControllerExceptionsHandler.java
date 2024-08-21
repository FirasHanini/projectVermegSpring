package com.example.test.controllerDemo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> invalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());

        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> invalidType(MethodArgumentTypeMismatchException exception) {
        String message = exception.getName() + " should be " + exception.getRequiredType();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


}