package com.vitaliy.forum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.services.service.CommentService;

// https://localhost:8443/api/admin/comment/updateIsActive
@RestController
@RequestMapping("/api/admin/comment")
public class CommentControllerAdmin {
    @Autowired
    private CommentService commentService;

    @PatchMapping("/updateActive")
    public ResponseEntity<Boolean> updateActive(@RequestParam int commentId, @RequestParam Boolean active) {
        commentService.toggleCommentActiveStatus(commentId, active);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam int commentId) {
        return ResponseEntity.ok(commentService.deleteCommentAdmin(commentId));
    }
}
