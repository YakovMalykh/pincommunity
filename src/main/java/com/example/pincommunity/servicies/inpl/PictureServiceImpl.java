package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.exceptions.PictureNotFoundException;
import com.example.pincommunity.models.Picture;
import com.example.pincommunity.repositories.PictureRepository;
import com.example.pincommunity.servicies.FileHandler;
import com.example.pincommunity.servicies.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class PictureServiceImpl implements ImageService<Picture> {
    @Value("${path.to.pictures.folder}")
    private String folderName;

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture saveImage(MultipartFile file, String newFileName, Picture image) {
        String filePathInFolder = FileHandler.saveFileIntoFolder(file, newFileName, folderName);
        byte[] preview = FileHandler.generatePreview(filePathInFolder);
        String contentType = file.getContentType();

        Picture picture = new Picture();
        picture.setFilePathInFolder(filePathInFolder);
        picture.setMediaType(contentType);
        picture.setPreview(preview);

        Picture savedPicture = pictureRepository.save(picture);//here I need get ID, for creating url and then write it into DB
        savedPicture.setPictureUrl(String.format("/pictures/preview/%s" + savedPicture.getId()));
        log.info("Picture was saved");
        return pictureRepository.save(savedPicture);
    }

    @Override
    public ResponseEntity<Picture> getImageById(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new PictureNotFoundException("Picture doesn't exist. PictureServiceImpl method getImageById"));
        log.info("get picture by id");
        return ResponseEntity.ok(picture);
    }

    @Override
    public Picture updateImage(Picture image, MultipartFile file) {
        String filePathInFolder = image.getFilePathInFolder();
        FileHandler.overwritesFileInFolder(file, filePathInFolder);
        byte[] preview = FileHandler.generatePreview(filePathInFolder);
        image.setPreview(preview);
        return pictureRepository.save(image);
    }

    @Override//нужен ли этот метод?
    public ResponseEntity<Void> deleteImageById(Long id) {
        pictureRepository.findById(id).orElseThrow(() -> new PictureNotFoundException("Picture doesn't exist. PictureServiceImpl method deleteImageById"));
        pictureRepository.deleteById(id);
        log.info("Picture was deleted");
        return ResponseEntity.ok().build();
    }
}
