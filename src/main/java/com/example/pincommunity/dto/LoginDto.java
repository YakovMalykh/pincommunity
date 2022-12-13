package com.example.pincommunity.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
public class LoginDto {
    @NotBlank
    @Email(message = "Email should be correct address")
    private String email;
    @NotBlank(message = "shouldn't be a blank")
    private String password;
}
