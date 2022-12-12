package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreatePinDto;
import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pins")
public class PinController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<PinDto> createPin(@RequestPart CreatePinDto createPinDto, @RequestPart MultipartFile file, Authentication authentication) {
        return ResponseEntity.ok(new PinDto());
    }

    @Operation(summary = "getting all pins of this member")
    @GetMapping
    public ResponseEntity<ResponseWrapperPinDto> getAllPins() {
        return ResponseEntity.ok(new ResponseWrapperPinDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinDto> getPinById(@PathVariable Long id) {
        return ResponseEntity.ok(new PinDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PinDto> removePinById(@PathVariable Long id) {
        return ResponseEntity.ok(new PinDto());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PinDto> updatePin (@PathVariable Long id, @RequestBody CreatePinDto createPinDto) {
        return ResponseEntity.ok(new PinDto());
    }

    @PatchMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePictureOfPin (@PathVariable Long id, @RequestPart MultipartFile file) {
        return ResponseEntity.ok().build();
    }


}
