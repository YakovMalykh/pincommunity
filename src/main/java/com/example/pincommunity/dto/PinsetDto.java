package com.example.pincommunity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PinsetDto {

    private Long id;
    private String pinsetName;
    private LocalDate releaseDate;
    private String pictureUrl;
    private String clubCity;
}
