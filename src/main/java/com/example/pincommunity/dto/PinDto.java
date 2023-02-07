package com.example.pincommunity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
public class PinDto {

    private Long id;
    private String pinsName;
    private LocalDate releaseDate;
    private String pictureUrl;
    private String clubCity;
    private String pinsetName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Username should be correct emailaddress")
    private String holdersUsername;// стоит ли давать достпу к его изменению?
}
