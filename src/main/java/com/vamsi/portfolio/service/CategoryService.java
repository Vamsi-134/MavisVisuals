package com.vamsi.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamsi.portfolio.model.Category;
import com.vamsi.portfolio.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(Category category) {

        if (categoryRepository.findByNameIgnoreCase(category.getName()).isPresent()) {
            return;
        }

        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void updateCategory(Category category) {

        Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);

        if (oldCategory == null) {
            return;
        }

        oldCategory.setName(category.getName());

        categoryRepository.save(oldCategory);
    }

}