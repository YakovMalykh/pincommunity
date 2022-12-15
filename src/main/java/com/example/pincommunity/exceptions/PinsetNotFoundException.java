package com.example.pincommunity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PinsetNotFoundException extends NotFoundException {
    public PinsetNotFoundException(String message) {
        super(message);
    }
}
