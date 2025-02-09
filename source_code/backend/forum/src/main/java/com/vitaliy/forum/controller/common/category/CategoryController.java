package com.vitaliy.forum.controller.common.category;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.services.service.CategoryService;
import com.vitaliy.forum.services.service.PostService;

@RestController
@RequestMapping("/api/common/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> listCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(listCategory);
    }

    // OKE
    
}
