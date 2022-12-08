package com.example.pincommunity.controllers;

import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.servicies.FileHandler;
import com.example.pincommunity.servicies.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/avatars")
public class AvatarController {
    @Qualifier("AvatarServiceImpl")
    private final ImageService<Avatar> avatarService;

    public AvatarController(ImageService<Avatar> avatarService) {
        this.avatarService = avatarService;
    }

    @Operation(summary = "getting avatar's preview by id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Doesn't exist")
    })
    @GetMapping("/preview/{avatarId}")
    public ResponseEntity<byte[]> getAvatarPreview(@PathVariable Long avatarId) {
        Avatar avatar = avatarService.getImageById(avatarId).getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getPreview().length);
        return ResponseEntity.status(200).headers(headers).body(avatar.getPreview());
    }

    @Operation(summary = "getting full avatar by id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Doesn't exist")
    })
    @GetMapping("/{avatarId}")
    public void getFullAvatar(@PathVariable Long avatarId, HttpServletResponse response) {
        Avatar avatar = avatarService.getImageById(avatarId).getBody();
        Path path = Path.of(avatar.getFilePathInFolder());
        String mediaType = avatar.getMediaType();
        FileHandler.uploadImageFromFolder(path,response,mediaType);
    }
}
