package com.vitaliy.forum.controller.common.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.dto.PostDetailResponse;
import com.vitaliy.forum.entity.Category;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.services.service.CategoryService;
import com.vitaliy.forum.services.service.PostService;

@RestController
@RequestMapping("/api/common/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getPostsByCategoryId/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Post> posts = postService.getPostsByCategoryId(category);
        return ResponseEntity.ok(posts);
    }
    
    // API lấy chi tiết bài viết theo ID
    @GetMapping("/getById/{postId}")
    public ResponseEntity<PostDetailResponse> getPostById(@PathVariable Integer postId) {
        // Lấy thông tin chi tiết bài viết từ service
        PostDetailResponse postDetail = postService.getPostDetailById(postId);

        if (postDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về lỗi 404 nếu không tìm thấy bài viết
        }

        return ResponseEntity.ok(postDetail); // Trả về thông tin chi tiết bài viết
    }   
}
