package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vamsi.portfolio.dto.MediaDTO;
import com.vamsi.portfolio.service.MediaService;

@Controller
public class GalleryController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/gallery/{category}")
    public String gallery(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        category = category.replace("-", " ");

        Page<MediaDTO> mediaPage =
                mediaService.getMediaByCategory(category, page);

        model.addAttribute("category", category);
        model.addAttribute("mediaList", mediaPage.getContent());

        model.addAttribute("currentPage", page);

        model.addAttribute("totalPages",
                mediaPage.getTotalPages());

        return "gallery";
    }

}