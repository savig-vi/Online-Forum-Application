package com.vitaliy.forum.services.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.exception.CategoryNotFoundException;
import com.vitaliy.forum.repository.CategoryRepository;
import com.vitaliy.forum.services.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<String> getCategoryNameById(int categoryId) {
        return categoryRepository.findCategoryNameById(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Tìm thể loại theo ID
    public Category getCategoryById(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElseThrow(() -> new RuntimeException("Category not found for id: " + categoryId));
    }

    // Tìm thể loại theo tên
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    // Tạo mới thể loại
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Cập nhật thể loại
    public Category updateCategory(int categoryId, Category categoryDetails) {
        Category category = getCategoryById(categoryId);
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setDescription(categoryDetails.getDescription());
        category.setCreatedBy(categoryDetails.getCreatedBy());
        return categoryRepository.save(category);
    }

    // Xóa thể loại
    public void deleteCategory(int categoryId) throws CategoryNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
    }

    // Kiểm tra nếu thể loại tồn tại
    public boolean isCategoryExist(int categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
