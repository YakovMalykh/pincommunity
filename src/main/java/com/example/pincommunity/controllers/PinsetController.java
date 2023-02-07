package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.*;
import com.example.pincommunity.servicies.PinsetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/pinsets")//  доступ создание и редактирование у ADMIN и SUPER_ADMIN, получать могут авторизованные
public class PinsetController {

    private final PinsetService pinsetService;
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @Operation(summary = "create new pinset",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "invalid data"),
                    @ApiResponse(responseCode = "401", description = "unauthorised"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "Club not found")

            })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<PinsetDto> createPinset(@Valid @RequestPart CreatePinsetDto createPinsetDto, @RequestPart MultipartFile file) {
        return pinsetService.createPinset(createPinsetDto, file);
    }
    @Operation(summary = "getting pinset by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "invalid data"),
                    @ApiResponse(responseCode = "401", description = "unauthorised"),
                    @ApiResponse(responseCode = "404", description = "Pinset not found")

            })
    @GetMapping("/{id}")//десь по идее должен быть доступ у всех авторизованных, чтобы посмотреть состав сета Почему? Разве простые гости не могу посмотреть?
    public ResponseEntity<PinsetDto> getPinsetById(@PathVariable Long id) {
        return pinsetService.getPinsetById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @Operation(summary = "remove pinset",
              responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "pinset not found")

    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePinsetById(@PathVariable Long id) {
        // при его вызове нужно затирать поле Pinset в Pin, но не удалять Pin
        // картинку нужно удалять из папки и из БД
        return pinsetService.removePinset(id);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @Operation(summary = "update pinset",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "invalid data"),
                    @ApiResponse(responseCode = "401", description = "unauthorised"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "pinset not found")

            })
    @PatchMapping("/{id}")
    public ResponseEntity<PinsetDto> updatePinset(@PathVariable Long id, @Valid @RequestBody CreatePinsetDto createPinsetDto) {
        return pinsetService.updatePinset(id, createPinsetDto);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @Operation(summary = "update picture of pinset",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "unauthorised"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "pinset not found")

            })
    @PatchMapping(value = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePictureOfPinset(@PathVariable Long id, @RequestPart MultipartFile file) {
        return pinsetService.updatePictureOfPinset(id, file);
    }

}
