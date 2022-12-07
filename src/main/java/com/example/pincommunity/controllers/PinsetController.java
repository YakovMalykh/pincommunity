package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pinsets")//  доступ у ADMIN и SUPER_ADMIN
public class PinsetController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<PinsetDto> createPinset(@RequestPart CreatePinsetDto createPinsetDto, @RequestPart MultipartFile file) {
        return ResponseEntity.ok(new PinsetDto());
    }

    @GetMapping("/{id}")//десь по идее должен быть доступ у всех авторизованных, чтобы посмотреть состав сета
    public ResponseEntity<PinsetDto> getPinsetById(@PathVariable Long id) {
        return ResponseEntity.ok(new PinsetDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PinsetDto> removePinsetById(@PathVariable Long id) {
        return ResponseEntity.ok(new PinsetDto());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PinsetDto> updatePin(@PathVariable Long id, @RequestBody CreatePinsetDto createPinsetDto) {
        return ResponseEntity.ok(new PinsetDto());
    }

    @PatchMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePictureOfPin(@PathVariable Long id, @RequestPart MultipartFile file) {
        return ResponseEntity.ok().build();
    }

}
