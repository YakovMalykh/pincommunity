package com.example.pincommunity.controllers;

import com.example.pincommunity.models.Picture;
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
@RequestMapping("/pictures")
public class PictureController {

    @Qualifier("PictureServiceImpl")
    private final ImageService<Picture> pictureService;

    public PictureController(ImageService<Picture> pictureService) {
        this.pictureService = pictureService;
    }

    @Operation(summary = "getting picture's preview by id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Doesn't exist")
    })
    @GetMapping("/preview/{pictureId}")
    public ResponseEntity<byte[]> getPicture(@PathVariable Long pictureId) {
        Picture picture = pictureService.getImageById(pictureId).getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getMediaType()));
        headers.setContentLength(picture.getPreview().length);
        return ResponseEntity.status(200).headers(headers).body(picture.getPreview());
    }
    @Operation(summary = "getting full picture by id", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Doesn't exist")
    })
    @GetMapping("/{pictureId}")
    public void getFullAvatar(@PathVariable Long pictureId, HttpServletResponse response) {
        Picture picture = pictureService.getImageById(pictureId).getBody();
        Path path = Path.of(picture.getFilePathInFolder());
        String mediaType = picture.getMediaType();
        FileHandler.uploadImageFromFolder(path, response, mediaType);
    }


}
