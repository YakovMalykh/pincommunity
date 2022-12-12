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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "update member", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorised"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "member or club doesn't exist")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@Valid @PathVariable Long id, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(id, memberDto);
    }

    @Operation(summary = "update avatar", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorised"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "member doesn't exist")
    })
    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAvatar(@PathVariable Long id, @RequestPart MultipartFile file) {
        memberService.updateAvatar(id, file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getting current member", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorised"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "member doesn't exist")
    })
    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMemberMe(Authentication authentication) {
        return memberService.getMemberMe(authentication);
    }
}
