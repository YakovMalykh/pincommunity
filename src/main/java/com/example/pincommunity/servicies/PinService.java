package com.example.pincommunity.servicies;

import com.example.pincommunity.dto.CreatePinDto;
import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface PinService {
    ResponseEntity<ResponseWrapperPinDto> getAllPins();
    ResponseEntity<PinDto> getPinById (Long id);
    ResponseEntity<PinDto> removePinById( Long id) ;
    ResponseEntity<PinDto> updatePin ( Long id,  CreatePinDto createPinDto);
    ResponseEntity<Void> updatePictureOfPin (Long id, MultipartFile file);
    ResponseEntity<PinDto> createPin(CreatePinDto createPinDto, MultipartFile file, Authentication auth);
}
