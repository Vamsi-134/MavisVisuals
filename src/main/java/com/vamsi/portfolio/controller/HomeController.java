package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vamsi.portfolio.service.MediaService;

@Controller
public class HomeController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("categoryList",
                mediaService.getCategoryCards());

        return "index";
    }
}