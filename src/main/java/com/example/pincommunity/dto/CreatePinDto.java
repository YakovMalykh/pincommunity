package com.example.pincommunity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class CreatePinDto {
    @NotBlank(message = "Description must not be empty")
    private String pinsName;
    @PastOrPresent ( message = "Date must not be future")
    private LocalDate releaseDate;
    @NotBlank(message = "Description must not be empty")
    private String clubCity;
    private String pinsetName;
}
