package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.exceptions.PinsetNotFoundException;
import com.example.pincommunity.mappers.PinsetMapper;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.models.Pinset;
import com.example.pincommunity.repositories.PinsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.example.pincommunity.Constatnts.ConstantsForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PinsetServiceTest {
    @Mock
    PinsetRepository pinsetRepository;
    @Mock
    PinsetRepository repoOfPins;
    @Mock
    PinsetMapper pinsetMapper;
    @Mock
    PictureServiceImpl pictureService;
    @InjectMocks
    PinsetServiceImpl pinsetService;

    @BeforeEach
    void setUp() {
        PINSET.setPinsetName("name");
        PINSET.setId(1L);
    }

    @Test
    void createPinset_successfully() {
        when(pinsetMapper.createPinsetDtoToPinset(CREATE_PINSET_DTO)).thenReturn(PINSET);
        when(pictureService.saveImage(any(MultipartFile.class), anyString(), any(Picture.class))).thenReturn(PICTURE);
        when(pinsetRepository.save(any(Pinset.class))).thenReturn(PINSET);
        when(pinsetMapper.pinsetToPinsetDto(any(Pinset.class))).thenReturn(PINSET_DTO);

        ResponseEntity<PinsetDto> response = pinsetService.createPinset(CREATE_PINSET_DTO, FILE);

        assertEquals(ResponseEntity.ok(PINSET_DTO), response);
    }

    @Test
    void getPinsetById_shouldThrowPinsetNotFoundException() {

        PinsetNotFoundException exception = assertThrows(PinsetNotFoundException.class, () -> pinsetService.getPinsetById(2L));

        assertEquals("Pinset by id " + 2 + " not found", exception.getMessage());
    }

    @Test
    void getPinsetById_successfully() {
        when(pinsetRepository.findById(anyLong())).thenReturn(Optional.of(PINSET));
        when(pinsetMapper.pinsetToPinsetDto(any(Pinset.class))).thenReturn(PINSET_DTO);

        ResponseEntity<PinsetDto> response = pinsetService.getPinsetById(1L);

        assertEquals(ResponseEntity.ok(PINSET_DTO), response);
    }


    @Test
    void updatePinset_shouldThrowPinsetNotFoundException() {
        Long id = 1L;
        when(pinsetRepository.findById(anyLong())).thenReturn(Optional.empty());
        PinsetNotFoundException exception = assertThrows(PinsetNotFoundException.class, () -> pinsetService.getPinsetById(id));

        assertEquals("Pinset by id " + id + " not found", exception.getMessage());

    }

    @Test
    void updatePinset_successfully() {
        when(pinsetRepository.findById(anyLong())).thenReturn(Optional.of(PINSET));
        when(pinsetMapper.pinsetToPinsetDto(any(Pinset.class))).thenReturn(PINSET_DTO);

        ResponseEntity<PinsetDto> response = pinsetService.getPinsetById(1L);

        assertEquals(ResponseEntity.ok(PINSET_DTO), response);

    }

    @Test
    void updatePictureOfPinset_shouldThrowPinsetNotFoundException() {
        Long id = 1L;
        when(pinsetRepository.findById(anyLong())).thenReturn(Optional.empty());
        PinsetNotFoundException exception = assertThrows(PinsetNotFoundException.class, () -> pinsetService.getPinsetById(id));

        assertEquals("Pinset by id " + id + " not found", exception.getMessage());
    }
    @Test
    void updatePictureOfPinset_successfully() {
        when(pinsetRepository.findById(anyLong())).thenReturn(Optional.of(PINSET));

        ResponseEntity<Void> response = pinsetService.updatePictureOfPinset(1L, FILE);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void removePinset() {
    }

    @Test
    void getAllPinsOfThisPinset() {
    }
}