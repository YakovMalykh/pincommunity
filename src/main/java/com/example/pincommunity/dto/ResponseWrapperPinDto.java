package com.example.pincommunity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperPinDto {
    private Long count;
    private List<PinDto> result;
}
