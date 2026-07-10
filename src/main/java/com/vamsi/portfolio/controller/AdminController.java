package com.vamsi.portfolio.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vamsi.portfolio.model.Media;
import com.vamsi.portfolio.service.MediaService;
import com.vamsi.portfolio.service.UploadService;

@Controller
public class AdminController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/upload")
    public String upload(

            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "coverImage", required = false) Boolean coverImage

    ) throws IOException {

        System.out.println("======================");
        System.out.println("Title       : " + title);
        System.out.println("Description : " + description);
        System.out.println("Category    : " + category);
        System.out.println("Type        : " + type);
        System.out.println("File Name   : " + file.getOriginalFilename());
        System.out.println("File Size   : " + file.getSize());
        System.out.println("Cover Image : " + (coverImage != null));
        System.out.println("======================");

        // Save file
        uploadService.saveFile(file, type);

        // Save data in database
        Media media = new Media();

        media.setTitle(title);
        media.setDescription(description);
        media.setCategory(category);
        media.setType(type);
        media.setFilename(file.getOriginalFilename());
        media.setCoverImage(coverImage != null);

        mediaService.saveMedia(media);

        return "redirect:/admin";
    }
}