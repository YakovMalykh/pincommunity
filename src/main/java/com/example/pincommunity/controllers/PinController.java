package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.CreatePinDto;
import com.example.pincommunity.dto.PinDto;
import com.example.pincommunity.dto.ResponseWrapperPinDto;
import com.example.pincommunity.servicies.PinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pins")
public class PinController {
    private final PinService pinService;

    @Operation(summary = "добавляем новый значок",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = PinDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<PinDto> createPin(@RequestPart("properties") CreatePinDto createPinDto, @RequestPart("image") MultipartFile file, Authentication auth) {
        return pinService.createPin(createPinDto, file, auth);
    }

    @Operation(summary = "getting all pins")
    @GetMapping
    public ResponseEntity<ResponseWrapperPinDto> getAllPins() {
        return pinService.getAllPins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinDto> getPinById(@PathVariable Long id) {
        return pinService.getPinById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PinDto> removePinById(@PathVariable Long id) {

        return pinService.removePinById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PinDto> updatePin(@PathVariable Long id, @RequestBody CreatePinDto createPinDto) {
        return pinService.updatePin(id, createPinDto);
    }

    @PatchMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePictureOfPin(@PathVariable Long id, @RequestPart MultipartFile file) {
        return pinService.updatePictureOfPin(id, file);
    }


}
