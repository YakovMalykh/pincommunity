package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.LoginDto;
import com.example.pincommunity.servicies.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        if (authService.login(loginDto.getEmail(), loginDto.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody CreateMemberDto createMemberDto) {
        if (authService.registration(createMemberDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
