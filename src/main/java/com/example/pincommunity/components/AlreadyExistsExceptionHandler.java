package com.example.pincommunity.components;

import com.example.pincommunity.exceptions.ClubAlreadyExists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AlreadyExistsExceptionHandler {

    @ExceptionHandler(ClubAlreadyExists.class)
    public ResponseEntity<Object> handlerClubAlreadyExists(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
