package com.vamsi.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamsi.portfolio.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}