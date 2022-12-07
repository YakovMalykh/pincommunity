package com.example.pincommunity.servicies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService<I> {

    /**
     * method fills and saves Picture or Avatar into DB
     * @param file MultipartFile
     * @param newFileName String
     * @param image - Picture or Avatar
     * @return saved Picture or Avatar
     */
    I saveImage(MultipartFile file,String newFileName, I image);

    ResponseEntity<I> getImageById(Long id);

    ResponseEntity<I> updateImage(I image);

    ResponseEntity<Void> deleteImageById(Long id);
}

