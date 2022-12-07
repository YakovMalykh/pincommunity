package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.servicies.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home ";
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {

        return "secured part of web service " + principal.getName();
    }

//    @Secured("ROLE_ADMIN")
    @GetMapping("/profile")
    public String pageProfile(Principal principal) {
        return "profile page " + principal.getName();
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> createMember(@RequestBody CreateMemberDto createMemberDto) {
        boolean saccess = memberService.createMember(createMemberDto);
        if (!saccess) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }

    }

}
