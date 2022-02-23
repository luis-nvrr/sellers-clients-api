package com.meli.sellerapi.infrastructure.exceptions;

import com.meli.sellerapi.application.dtos.ExceptionResponse;
import com.meli.sellerapi.domain.exceptions.AlreadyFollowingException;
import com.meli.sellerapi.domain.exceptions.NotFollowingSellerException;
import com.meli.sellerapi.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(UserNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<ExceptionResponse> handle(AlreadyFollowingException ex) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFollowingSellerException.class)
    public ResponseEntity<ExceptionResponse> handle(NotFollowingSellerException ex) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
