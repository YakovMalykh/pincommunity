package com.example.pincommunity.servicies.inpl;

import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.repositories.AvatarRepository;
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
public class AvatarServiceImpl implements ImageService<Avatar> {
    @Value("${path.to.avatars.folder}")
    private String folderName;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Avatar saveImage(MultipartFile file, String newFileName, Avatar image) {
        String filePathInFolder = FileHandler.saveFileIntoFolder(file, newFileName, folderName);
        byte[] preview = FileHandler.generatePreview(filePathInFolder);
        String contentType = file.getContentType();

        Avatar avatar = new Avatar();
        avatar.setFilePathInFolder(filePathInFolder);
        avatar.setMediaType(contentType);
        avatar.setPreview(preview);

        Avatar savedAvatar = avatarRepository.save(avatar);//here I need get ID, for creating url and then write it into DB
        savedAvatar.setAvatarUrl(String.format("/avatars/%s" + savedAvatar.getId()));
        log.info("Avatar was saved");
        return avatarRepository.save(savedAvatar);
    }

    @Override
    public ResponseEntity<Avatar> getImageById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Avatar> updateImage(Avatar image) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteImageById(Long id) {
        return null;
    }
}
