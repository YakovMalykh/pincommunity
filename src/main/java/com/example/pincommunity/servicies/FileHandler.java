package com.example.pincommunity.servicies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
public class FileHandler {

    public static String saveFileIntoFolder(MultipartFile file, String newFileName, String folderName) {

        Path filePath = Path.of(folderName, newFileName + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            }
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
        }
        log.info("the file's path was got");
        return filePath.toString();
    }

    private static String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }

    /**
     * method downsizes a file for storing in DB
     * @param filePath String
     * @return byte[]
     */
    public static byte[] generatePreview(String filePath) {
        Path path = Path.of(filePath);
        log.info("method generatePreview started");
        try {
            try (
                    InputStream is = Files.newInputStream(path);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                BufferedImage image = ImageIO.read(bis);

                int height = image.getHeight() / (image.getWidth() / 100);
                BufferedImage prewiew = new BufferedImage(100, height, image.getType());
                Graphics2D graphics = prewiew.createGraphics();
                graphics.drawImage(image, 0, 0, 100, height, null);
                graphics.dispose();
                ImageIO.write(prewiew, getExtension(path.getFileName().toString()), baos);
                return baos.toByteArray();
            }
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
            return null;
        }

    }
}
