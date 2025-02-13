package com.vitaliy.forum.services.service;

import java.util.List;
import java.util.Optional;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.exception.CategoryNotFoundException;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int categoryId);
    Category getCategoryByName(String categoryName);
    Category createCategory(Category category);
    Category updateCategory(int categoryId, Category categoryDetails);
    void deleteCategory(int categoryId) throws CategoryNotFoundException;
    boolean isCategoryExist(int categoryId);
    Optional<String> getCategoryNameById(int categoryId);
}