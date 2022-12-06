package com.example.pincommunity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PinDto {

    private Long id;
    private String pinsName;
    private LocalDate releaseDate;
    private String pictureUrl;
    private String clubCity;
    private String pinsetName;
    private String holdersUsername;// стоит ли давать достпу к его изменению?
}
