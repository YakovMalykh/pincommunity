package com.example.pincommunity.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ClubDto {
    private Long id;
    private String city;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "here should be used an email")
    private String adminUsername;
    private String clubAvatarUrl;
}
