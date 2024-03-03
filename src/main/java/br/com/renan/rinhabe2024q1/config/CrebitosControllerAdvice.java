package br.com.renan.rinhabe2024q1.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CrebitosControllerAdvice {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity handleValidationException() {
        return ResponseEntity.unprocessableEntity().build();
    }

}
