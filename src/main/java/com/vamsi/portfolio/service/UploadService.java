package com.vamsi.portfolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    private static final String IMAGE_PATH = "D:/PortfolioUploads/images/";
    private static final String VIDEO_PATH = "D:/PortfolioUploads/videos/";

    public void saveFile(MultipartFile file, String type) throws IOException {

        String folder = type.equalsIgnoreCase("Image") ? IMAGE_PATH : VIDEO_PATH;

        Path path = Paths.get(folder, file.getOriginalFilename());

        Files.createDirectories(path.getParent());

        file.transferTo(path);

        System.out.println("==================================");
        System.out.println("File Saved Successfully!");
        System.out.println("Location : " + path.toAbsolutePath());
        System.out.println("==================================");
    }
}