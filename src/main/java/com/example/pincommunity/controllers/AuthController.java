package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.LoginDto;
import com.example.pincommunity.servicies.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/registration")
    public ResponseEntity<?> createMember(@RequestBody CreateMemberDto createMemberDto) {
        boolean saccess = memberService.createMember(createMemberDto);
        if (!saccess) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        boolean isMemberExists = memberService.isUserExists(loginDto.getEmail());
        if (!isMemberExists) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

}
