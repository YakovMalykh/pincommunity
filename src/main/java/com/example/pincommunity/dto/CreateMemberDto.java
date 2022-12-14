package com.example.pincommunity.dto;

import com.example.pincommunity.constants.Role;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
public class CreateMemberDto {
    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email should be correct address")
    private String email;
    @NotBlank(message = "shouldn't be a blank")
    private String password;
    @NotBlank(message = "shouldn't be a blank")
    private String fullName;
    private Role role;
}
