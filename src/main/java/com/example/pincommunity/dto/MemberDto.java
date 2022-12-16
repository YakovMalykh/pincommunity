package com.example.pincommunity.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@Validated
public class MemberDto {

    private Long id;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email should be correct address")
    private String email;
    private String fullName;
    private LocalDate birthday;
    private String avatarUrl;
    private String clubCity;
}
