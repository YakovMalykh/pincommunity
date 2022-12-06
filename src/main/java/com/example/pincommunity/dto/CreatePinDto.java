package com.example.pincommunity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class CreatePinDto {
    @NotBlank
    private String pinsName;
    @Past
    private LocalDate releaseDate;
    @NotBlank
    private String clubCity;
    private String pinsetName;
}
