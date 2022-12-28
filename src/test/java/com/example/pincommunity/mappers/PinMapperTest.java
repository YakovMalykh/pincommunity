package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Pin;
import com.example.pincommunity.models.Pinset;
import com.example.pincommunity.repositories.ClubRepository;
import com.example.pincommunity.repositories.PinsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PinMapperTest {
    @Mock
    PinsetRepository pinsetRepository;
    @Mock
    ClubRepository clubRepository;
    @InjectMocks
    PinMapper pinMapper = Mappers.getMapper(PinMapper.class);

    @BeforeEach
    void setUp() {
        CLUB.setCity(CLUB_CITY);

        CREATE_PIN_DTO.setClubCity(CLUB_CITY);
        CREATE_PIN_DTO.setPinsetName("testNameForPinset");
        CREATE_PIN_DTO.setReleaseDate(TEST_DATE);
        CREATE_PIN_DTO.setPinsName("testNameForPin");

        PINSET.setId(1L);
        PINSET.setPinsetName("pinsetName");
        PINSET.setReleaseDate(TEST_DATE);
        PINSET.setPinsetPictureId(PICTURE);
        PINSET.setOriginClub(CLUB);

        PIN.setId(1L);
        PIN.setPinsName("testNameForPin");
        PIN.setReleaseDate(TEST_DATE);
        PIN.setPicture(PICTURE);
        PIN.setOriginClub(CLUB);
        PIN.setPinset(PINSET);
        PIN.setHolder(MEMBER);
    }

    @Test
    void clubToString() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        Club result = pinMapper.stringToClub(CLUB_CITY);
        assertEquals(CLUB, result);
    }

    @Test
    void clubToPinset() {
        when(pinsetRepository.findByPinsetNameIgnoreCase(anyString())).thenReturn(Optional.of(PINSET));
        Pinset result = pinMapper.stringToPinset(PINSET.getPinsetName());
        assertEquals(PINSET, result);
    }

    @Test
    void createPinDtoToPin_successfully() {
        when(pinsetRepository.findByPinsetNameIgnoreCase(anyString())).thenReturn(Optional.of(PINSET));
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        Pin pin = pinMapper.createPinDtoToPin(CREATE_PIN_DTO);

        assertEquals(CLUB_CITY, pin.getOriginClub().getCity());
        assertEquals(TEST_DATE, pin.getReleaseDate());
        assertEquals("pinsetName", pin.getPinset().getPinsetName());
        assertEquals("testNameForPin", pin.getPinsName());
    }

    @Test
    void PinToPinDto_successfully() {
        PinDto pinDto = pinMapper.pinToPinDto(PIN);
        assertEquals(CLUB_CITY, pinDto.getClubCity());
        assertEquals(TEST_DATE, pinDto.getReleaseDate());
        assertEquals("pinsetName", pinDto.getPinsetName());
        assertEquals("testNameForPin", pinDto.getPinsName());
    }

    @Test
    void updatePinFromCreatePinDto_successfully() {
        Long id = 1l;
        CLUB.setCity("Moscow");
        PINSET.setPinsetName("update");
        CREATE_PIN_DTO.setPinsetName("update");
        CREATE_PIN_DTO.setClubCity("Moscow");
        CREATE_PIN_DTO.setPinsName(null);
        CREATE_PIN_DTO.setReleaseDate(LocalDate.parse("01-01-2022", DateTimeFormatter.ofPattern(DATE_FORMAT)));

        when(pinsetRepository.findByPinsetNameIgnoreCase(anyString())).thenReturn(Optional.of(PINSET));
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));
        pinMapper.updatePinFromCreatePinDto(CREATE_PIN_DTO, PIN);

        assertEquals(id, PIN.getId());
        assertEquals("testNameForPin", PIN.getPinsName());
        assertEquals(LocalDate.parse("01-01-2022", DateTimeFormatter.ofPattern(DATE_FORMAT)), PIN.getReleaseDate());
        assertEquals(PICTURE.getPictureUrl(), PIN.getPicture().getPictureUrl());
        assertEquals("Moscow", PIN.getOriginClub().getCity());
        assertEquals("update", PIN.getPinset().getPinsetName());
    }
}
