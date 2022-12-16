package com.example.pincommunity.mappers;

import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.exceptions.ClubNotFoundException;
import com.example.pincommunity.models.Pinset;
import com.example.pincommunity.repositories.ClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PinsetMapperTest {

    @Mock
    ClubRepository clubRepository;
    @InjectMocks
    PinsetMapper mapper = Mappers.getMapper(PinsetMapper.class);

    @BeforeEach
    void setUp() {
        CREATE_PINSET_DTO.setClubCity(CLUB_CITY);
        CREATE_PINSET_DTO.setPinsetName("test");
        CREATE_PINSET_DTO.setReleaseDate(TEST_DATE);

        CLUB.setCity(CLUB_CITY);

        PINSET.setId(1L);
        PINSET.setPinsetName("pinset name");
        PINSET.setReleaseDate(TEST_DATE);
        PINSET.setPinsetPictureId(PICTURE);
        PINSET.setOriginClub(CLUB);

        PICTURE.setPictureUrl(TEST_URL);
    }

    @Test
    void createPinsetDtoToPinset_shouldThrowClubNotFoundException() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.empty());

        ClubNotFoundException exception = assertThrows(ClubNotFoundException.class, () ->
                mapper.createPinsetDtoToPinset(CREATE_PINSET_DTO));

        assertEquals("Club not found in city: " + CLUB_CITY, exception.getMessage());
    }

    @Test
    void createPinsetDtoToPinset_successfully() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));

        Pinset pinset = mapper.createPinsetDtoToPinset(CREATE_PINSET_DTO);

        assertEquals(CLUB_CITY, pinset.getOriginClub().getCity());
        assertEquals(TEST_DATE, pinset.getReleaseDate());
        assertEquals("test", pinset.getPinsetName());
    }


    @Test
    void pinsetToPinsetDto() {
        PinsetDto result = mapper.pinsetToPinsetDto(PINSET);
        assertEquals(1L, result.getId());
        assertEquals("pinset name", result.getPinsetName());
        assertEquals(TEST_DATE, result.getReleaseDate());
        assertEquals(TEST_URL, result.getPictureUrl());
        assertEquals(CLUB_CITY, result.getClubCity());
    }

    @Test
    void pictureToString() {
        String result = mapper.pictureToString(PICTURE);
        assertEquals(TEST_URL, result);
    }

    @Test
    void clubToString() {
        String result = mapper.clubToString(CLUB);
        assertEquals(CLUB_CITY, result);
    }

    @Test
    void updatePinsetFromCreatePinsetDto_shouldThrowClubNotFoundException() {
        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.empty());
        assertThrows(ClubNotFoundException.class, () -> mapper.updatePinsetFromCreatePinsetDto(CREATE_PINSET_DTO, PINSET));
    }

    @Test
    void updatePinsetFromCreatePinsetDto_successfully() {
        CLUB.setCity("Moscow");
        CREATE_PINSET_DTO.setPinsetName("update");
        CREATE_PINSET_DTO.setClubCity("Moscow");

        when(clubRepository.findByCityIgnoreCase(anyString())).thenReturn(Optional.of(CLUB));

        mapper.updatePinsetFromCreatePinsetDto(CREATE_PINSET_DTO, PINSET);

        assertEquals(1L, PINSET.getId());
        assertEquals("update", PINSET.getPinsetName());
        assertEquals(TEST_DATE, PINSET.getReleaseDate());
        assertEquals("Moscow", PINSET.getOriginClub().getCity());
        assertEquals(PICTURE, PINSET.getPinsetPictureId());


    }

}