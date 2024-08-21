package com.example.test.serviceDemo;

import lombok.AllArgsConstructor;

import java.util.Optional;


@AllArgsConstructor
public class ValidationResult <T> {
    private Optional<T> value;
    private ErrorResponse error;


    public static <T> ValidationResult<T> valid(final T value) throws Exception {
        if(value==null)
            throw new Exception("Value can't be null");
        return new ValidationResult<>(Optional.of(value),null);
    }

    public static <T> ValidationResult<T> invalid(final ErrorResponse errorResponse) throws Exception {//Exception non null
        if(errorResponse==null)
            throw new Exception("errorResponse can't be null");
        return new ValidationResult<>(Optional.empty(),errorResponse);
    }
    public boolean isValid() {
        return value.isPresent();
    }

    public T get() {
        return value.get();
    }

    public ErrorResponse getError() {
        return error;
    }

}




