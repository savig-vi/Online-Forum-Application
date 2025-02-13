package com.vitaliy.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.services.service.CategoryService;

@RestController()
@RequestMapping("/api/admin/category")
public class CategoryControllerAdmin {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryById")
    public ResponseEntity<Category> getCategoryBydId(@RequestParam int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/updateCategory")
    public void updateCategory(@RequestBody Category category) {
        int categoryId = category.getCategoryId();
        categoryService.updateCategory(categoryId, category);
    }
    
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestParam int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }
}
