package com.vitaliy.forum.controller.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vitaliy.forum.dto.CommentRequestDTO;
import com.vitaliy.forum.entity.Comment;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.ResourceNotFoundException;
import com.vitaliy.forum.services.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getCommentsByPostId/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable int postId) {
        List<Comment> listComment = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(listComment);
    }

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("Session user: " + session.getId());
        if (user == null) {
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập!");
        }

        try {
            Comment savedComment = commentService.createComment(user.getUserId(), commentDTO);
            return ResponseEntity.ok(savedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<?> updateComment (
            @PathVariable int commentId,
            @RequestBody Map<String, String> request,
            HttpSession session) {
        
        User currentUser = (User) session.getAttribute("user"); // Lấy user từ session
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String newContent = request.get("content");
            Comment updatedComment = commentService.updateComment(commentId, newContent, currentUser);
            return ResponseEntity.ok(updatedComment);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable int commentId,
            HttpSession session) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            commentService.deleteComment(commentId, currentUser);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}