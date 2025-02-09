package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Category;

public interface CategoryService {
    Category saveCategory(Category category);
    Category getCategoryById(int id);
    Category getCategoryByName(String categoryName);
    List<Category> getAllCategory();
    void updateCategory(Category category);
    void deleteCategory(int id);
}
