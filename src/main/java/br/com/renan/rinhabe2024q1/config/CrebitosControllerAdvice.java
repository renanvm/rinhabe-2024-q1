package br.com.renan.rinhabe2024q1.config;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class CrebitosControllerAdvice {

    @ExceptionHandler({
            ValidationException.class,
    })
    public void handleValidationException() {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
