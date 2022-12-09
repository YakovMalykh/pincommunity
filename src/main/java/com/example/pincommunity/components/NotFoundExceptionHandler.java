package com.example.pincommunity.components;

import com.example.pincommunity.exceptions.AvatarNotFoundException;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.exceptions.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<Object> handleClubException(Exception exception) {
//        exception.printStackTrace();
        log.info(exception.getMessage());
        return new ResponseEntity<Object>("there is no club in this city", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Object> handlerMemberException(Exception exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<Object>("no such member found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<Object> handlerAvatarException(Exception exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<Object>("avatar not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handlerUsernameException(Exception exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<Object>(exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
