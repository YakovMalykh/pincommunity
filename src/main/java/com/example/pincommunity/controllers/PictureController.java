package com.example.pincommunity.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pictures")
public class PictureController {

    // здесь нам нужно только получение картинки, по аналогии с выпускной работой
    @Operation(description = "getting picture by id")
    @GetMapping("/{pictureId}")
    public ResponseEntity<byte[]> getPicture(@PathVariable Long pictureId) {
        return null;
    }

}
