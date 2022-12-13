package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.LoginDto;
import com.example.pincommunity.servicies.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {

    private final AuthService authService;

    @Operation(description = "Login", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "invalid data"),
            @ApiResponse(responseCode = "403", description = "forbidden")})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.login(loginDto.getEmail(), loginDto.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @Operation(description = "Registration", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "member already exists, or invalid data")})
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody CreateMemberDto createMemberDto) {
        if (authService.registration(createMemberDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
