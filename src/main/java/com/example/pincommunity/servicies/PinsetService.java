package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.CreatePinsetDto;
import com.example.pincommunity.dto.PinsetDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import com.example.pincommunity.exceptions.PinsetNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PinsetService {
    ResponseEntity<PinsetDto> createPinset(CreatePinsetDto createPinsetDto, MultipartFile file);

    /**
     * @throws PinsetNotFoundException status 404
     */
    ResponseEntity<PinsetDto> getPinsetById(Long id);

    ResponseEntity<PinsetDto> updatePinset(Long id, CreatePinsetDto createPinsetDto);

    ResponseEntity<Void> updatePictureOfPinset(Long id, MultipartFile file);

    ResponseEntity<Void> removePinset(Long id);

    ResponseEntity<ResponseWrapperPinDto> getAllPinsOfThisPinset(Long pinsetId);
}
