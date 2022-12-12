package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/clubs")// к этому контроллеру доступ только у SUPER_ADMIN
public class ClubController {

    @PostMapping
    public ResponseEntity<ClubDto> createClub(@Valid @RequestBody CreateClubDto createClubDto) {
        return ResponseEntity.ok(new ClubDto());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @RequestBody CreateClubDto createClubDto) {
        return ResponseEntity.ok(new ClubDto());
    }
}
