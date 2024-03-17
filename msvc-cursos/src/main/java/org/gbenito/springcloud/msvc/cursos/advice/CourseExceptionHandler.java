package org.gbenito.springcloud.msvc.cursos.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CourseExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidArguments(MethodArgumentNotValidException exception){
        Map<String, Object> handleError = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> reason = new HashMap<>();

        errors.put("code", exception.getStatusCode().value());
        errors.put("message", "An error has occurred");

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            reason.put(error.getField(), error.getDefaultMessage());
        });

        errors.put("reason", reason);
        handleError.put("error", errors);

        return ResponseEntity.status(exception.getStatusCode()).body(handleError);
    }
}
