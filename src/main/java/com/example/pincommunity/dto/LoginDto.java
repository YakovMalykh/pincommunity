package com.example.pincommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank
    @Email(message = "Email should be correct address")
    private String email;
    @NotBlank(message = "shouldn't be a blank")
    private String password;
}
