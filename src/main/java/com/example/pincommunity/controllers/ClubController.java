package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.servicies.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/clubs")// к этому контроллеру доступ только у SUPER_ADMIN
public class ClubController {

    private final ClubService clubService;

    @Operation(
            summary = "create new club",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Club already exists, or invalid data"),
                    @ApiResponse(responseCode = "401", description = "unauthorized"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "member not found, who can be made an administrator")
            }
    )
    @PostMapping
    public ResponseEntity<ClubDto> createClub(@Valid @RequestBody CreateClubDto createClubDto) {
        return clubService.createClub(createClubDto);
    }

    @Operation(
            summary = "update club",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "invalid data"),
                    @ApiResponse(responseCode = "401", description = "unauthorized"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "club doesn't exist or member not found, who can be made an administrator")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @Valid @RequestBody ClubDto clubDto) {
        return clubService.updateClub(id, clubDto);
    }

    @Operation(
            summary = "update avatar of club",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "unauthorized"),
                    @ApiResponse(responseCode = "403", description = "forbidden"),
                    @ApiResponse(responseCode = "404", description = "club doesn't exist")
            }
    )
    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateClubAvatar(@PathVariable Long id, @RequestPart MultipartFile file) {
        return clubService.updateClubAvatar(id, file);
    }

    @Operation(summary = "get club by id")
    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id) {
        return clubService.getClubById(id);
    }
}
