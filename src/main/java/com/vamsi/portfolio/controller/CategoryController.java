package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vamsi.portfolio.model.Category;
import com.vamsi.portfolio.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add-category")
    public String addCategory(@RequestParam String name) {

        Category category = new Category();
        category.setName(name);

        categoryService.saveCategory(category);

        return "redirect:/admin";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Integer id) {

        categoryService.deleteCategory(id);

        return "redirect:/admin";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable Integer id, Model model) {

        model.addAttribute("category",
                categoryService.getCategoryById(id));

        return "edit-category";
    }

    @PostMapping("/update-category")
    public String updateCategory(Category category) {

        categoryService.updateCategory(category);

        return "redirect:/admin";
    }

}