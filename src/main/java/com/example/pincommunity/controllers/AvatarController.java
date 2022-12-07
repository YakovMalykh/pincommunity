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
@RequestMapping("/avatars")
public class AvatarController {

    // здесь нужно прописать только получение аватара
    @Operation(description = "getting avatar by id")
    @GetMapping("/{avatarId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long avatarId) {
        return null;
    }
}
