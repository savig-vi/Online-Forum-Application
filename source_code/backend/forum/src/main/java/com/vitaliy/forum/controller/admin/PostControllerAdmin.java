package com.vitaliy.forum.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vitaliy.forum.dto.post.PostResponseDTO;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.exception.BusinessException;
import com.vitaliy.forum.services.service.PostService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/admin/post")
public class PostControllerAdmin {

    @Autowired
    private PostService postService;

    // ẨN HIỆN BÀI VIẾT
    @PatchMapping("/updateIsActive/{postId}")
    public ResponseEntity<Void> updateIsActive(@PathVariable("postId") int postId, @RequestParam Boolean isActive) {
        postService.updateIsActiveById(postId, isActive);
        return ResponseEntity.noContent().build();
    }

    // XÓA BÀI VIẾT
    @DeleteMapping("/delete")
    public ResponseEntity<PostResponseDTO> delete (
            @RequestParam int postId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vui lòng đăng nhập bằng tài khoản admin để thực hiện hành động này.");
        }

        try {
            int categoryId = postService.deletePostAdmin(postId);
            return ResponseEntity.ok(new PostResponseDTO("Xóa thành công", Map.of("categoryId", categoryId)));
        } catch (BusinessException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new PostResponseDTO(e.getMessage()));
        }
    }
}

