package com.example.pincommunity.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {

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
}
