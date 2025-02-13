package com.vitaliy.forum.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.Post;
import com.vitaliy.forum.services.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable int postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/getPostsByCategoryId/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<Post> posts = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }
    
    @RequestMapping("/getPostsByCategoryId/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategoryId(@PathVariable int categoryId) {
        List<Post> posts = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }
}
