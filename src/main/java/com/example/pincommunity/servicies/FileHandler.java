package com.example.pincommunity.servicies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
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
            workingWithInputAndOutputStreams(file, filePath);
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
        }
        log.info("the file's path was got");
        return filePath.toString();
    }

    public static void overwritesFileInFolder(MultipartFile file, String path) {
        Path filePath = Path.of(path);
        try {
            Files.deleteIfExists(filePath);
            workingWithInputAndOutputStreams(file, filePath);
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
        }
        log.info("the file's path was got");
    }

    private static String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }

    private static void workingWithInputAndOutputStreams(MultipartFile file, Path filePath) throws IOException {
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
    }

    /**
     * method downsizes a file for storing in DB
     *
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
                BufferedImage preview = new BufferedImage(100, height, image.getType());
                Graphics2D graphics = preview.createGraphics();
                graphics.drawImage(image, 0, 0, 100, height, null);
                graphics.dispose();
                ImageIO.write(preview, getExtension(path.getFileName().toString()), baos);
                return baos.toByteArray();
            }
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * method is used for getting picture or avatar from folder. It allows get full image, not preview
     */
    public static void uploadImageFromFolder(Path filePath, HttpServletResponse response, String contentType) {
        try {
            try (
                    InputStream is = Files.newInputStream(filePath);
                    OutputStream os = response.getOutputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                response.setStatus(200);
                response.setContentType(contentType);
                bis.transferTo(bos);
            }
        } catch (IOException e) {
            log.info("something wrong with file");
            e.printStackTrace();
        }
    }
}
