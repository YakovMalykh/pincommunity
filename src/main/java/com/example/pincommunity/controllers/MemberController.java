package com.example.pincommunity.controllers;

import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.servicies.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new MemberDto());
    }

    @Operation(summary = "update avatar",responses = {
            @ApiResponse(responseCode = "404", description = "member doesn't exist"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAvatar (@PathVariable Long id, @RequestPart MultipartFile file) {
        memberService.updateAvatar(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMember(Authentication authentication) {
        return ResponseEntity.ok(new MemberDto());
    }
}
