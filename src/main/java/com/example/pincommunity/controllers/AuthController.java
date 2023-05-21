package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.servicies.AuthService;
import com.example.pincommunity.servicies.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
@CrossOrigin(value = "http://localhost:8080")
public class AuthController {
    private final ClubService clubService;
    private final AuthService authService;

    @GetMapping("/login")
    public String loginForm() {
        log.info("method GET login in progress");
        // model.addAttribute("login",new LoginDto());
        return "layouts/login";
    }
    @GetMapping("/home")
    public String home() {
        log.info("method hm in progress");
        // model.addAttribute("login",new LoginDto());
        return "home";
    }
    @PostMapping("/loginpost")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        log.info("method POST in progress");
        if (authService.login(email, password)) {
            log.info("method loginpost home");
            return "layouts/home";
        } else {
            log.info("method loginpost login");
            return "layouts/login";
        }
    }
    @ModelAttribute(name = "clubs")
    public List<ClubDto> clubsView() {
        return clubService.getAllClubsH();
    }
    /*  @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, Model model) {
        log.info("method POST in progress");
        model.addAttribute("login",loginDto)
       return "layouts/home";
     //   if (authService.login(loginDto.getEmail(), loginDto.getPassword())) {
     //       return "layouts/home";
     //   } else {
     //       return ResponseEntity.status(HttpStatus.FORBIDDEN).build().toString();
     //  @RequestBody LoginDto loginDto }
    }
*/

//    @Operation(description = "Login", responses = {
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "400", description = "invalid data"),
//            @ApiResponse(responseCode = "403", description = "forbidden")})
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
//        if (authService.login(loginDto.getEmail(), loginDto.getPassword())) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }

    @ModelAttribute("createMemberDto")
    public CreateMemberDto createMemberDto() {
        return new CreateMemberDto();
    }

    @GetMapping("/registration")
    public String regForm() {
        log.info("method GET reg in progress");
        // model.addAttribute("login",new LoginDto());
        return "layouts/reg";
    }

    @Operation(description = "Registration", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "member already exists, or invalid data")})
    @PostMapping("/registration")
    public String registration(  @Valid @ModelAttribute("createMemberDto")  CreateMemberDto createMemberDto, BindingResult result) {
        log.info("method POST reg in progress");
        if (result.hasErrors()) {
            log.info("method result.hasErrors() in progress");
            return "/registration";

        }
        if (authService.registration(createMemberDto)) {
            return "redirect:/registration?success";
        } else {
            log.info("method redirect:/registration?error in progress");
            return "redirect:/registration?error";
        }
    }

}
