package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vamsi.portfolio.service.MediaService;

@Controller
public class GalleryController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/gallery/{category}")
    public String gallery(
            @PathVariable String category,
            Model model) {

        category = category.replace("-", " ");

        model.addAttribute("category", category);
        model.addAttribute("mediaList",
                mediaService.getMediaByCategory(category));

        return "gallery";
    }

}