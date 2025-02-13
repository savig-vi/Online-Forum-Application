package com.vitaliy.forum.controller.user;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.dto.post.CreatePostRequestDTO;
import com.vitaliy.forum.dto.post.PostResponseDTO;
import com.vitaliy.forum.dto.post.UpdatePostRequestDTO;
import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.BusinessException;
import com.vitaliy.forum.services.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController("PostControllerUser")
@RequestMapping("/api/user/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequestDTO request, 
                                      HttpSession session) {
        // Lấy thông tin user từ session
        User author = (User) session.getAttribute("user");
        
        // Kiểm tra đăng nhập
        if (author == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Vui lòng đăng nhập để tạo bài viết");
        }

        // Validate input
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tiêu đề không được để trống");
        }
        
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nội dung không được để trống");
        }

        // Tạo bài viết
        try {
            Post createdPost = postService.createPost(request, author);
            return ResponseEntity.ok(Collections.singletonMap("postId", createdPost.getPostId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi tạo bài viết: " + e.getMessage());
        }
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable int postId,
            @RequestBody UpdatePostRequestDTO request,
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new PostResponseDTO("Vui lòng đăng nhập"));
        }

        try {
            Post post = postService.updatePost(postId, request, user.getUserId());
            return ResponseEntity.ok(new PostResponseDTO("Cập nhật thành công", post));
        } catch (BusinessException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new PostResponseDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<PostResponseDTO> deletePost(
            @PathVariable int postId,
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new PostResponseDTO("Vui lòng đăng nhập"));
        }

        try {
            int categoryId = postService.deletePost(postId, user.getUserId());
            return ResponseEntity.ok(new PostResponseDTO("Xóa thành công", Map.of("categoryId", categoryId)));
        } catch (BusinessException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new PostResponseDTO(e.getMessage()));
        }
    }
}
