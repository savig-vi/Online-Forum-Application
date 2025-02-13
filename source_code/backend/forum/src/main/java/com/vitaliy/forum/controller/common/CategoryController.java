package com.vitaliy.forum.controller.common;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.services.service.CategoryService;

@RestController
@RequestMapping(value =  "/api/category", produces = "application/json; charset=UTF-8")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> listCategory = categoryService.getAllCategories();
        return ResponseEntity.ok(listCategory);
    }

    @GetMapping("/getCategoryNameById/{categoryId}")
    public ResponseEntity<Optional<String>> getCategoryNameById(@PathVariable int categoryId) {
        Optional<String> categoryName = categoryService.getCategoryNameById(categoryId);
        return ResponseEntity.ok(categoryName);
    }
}
