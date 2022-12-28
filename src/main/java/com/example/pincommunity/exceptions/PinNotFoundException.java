package com.example.pincommunity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PinNotFoundException extends NotFoundException {
    public PinNotFoundException(String message) {
        super(message);
    }
}
