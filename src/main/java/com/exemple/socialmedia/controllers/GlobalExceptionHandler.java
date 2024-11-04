package com.exemple.socialmedia.controllers;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.exemple.socialmedia.domain.Exception.HttpException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    logger.error("Constraint violation exception: {}. Request details: {}", ex.getMessage(),
        request.getDescription(false), ex);
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String propertyPath = violation.getPropertyPath().toString();
      String message = violation.getMessage();
      errors.put(propertyPath, message);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    logger.error("Method argument not valid exception: {}. Request details: {}", ex.getMessage(),
        request.getDescription(false), ex);
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  // Handle other custom HttpExceptions if needed
  @ExceptionHandler(HttpException.class)
  public ResponseEntity<Map<String, String>> handleHttpException(HttpException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    logger.error("Http exception: {}. Request details: {}", ex.getMessage(), request.getDescription(false), ex);
    error.put("error", ex.getMessage());
    return ResponseEntity.status(ex.getStatus()).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleException(Exception ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    logger.error("Exception: {}. Request details: {}", ex.getMessage(), request.getDescription(false), ex);
    error.put("error", "Internal server error.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
