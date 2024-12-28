package com.frun36.mountains.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.dao.DataAccessException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleDbError(SQLException e) {
        return ResponseEntity.internalServerError().body(e.getMessage() + "\nCause: " + e.getCause());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDbError(DataAccessException e) {
        return ResponseEntity.internalServerError().body(e.getMessage() + "\nCause: " + e.getCause());
    }
}
