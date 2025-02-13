package com.vitaliy.forum.controller.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.services.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> testApi(HttpSession session) {
        return ResponseEntity.ok("Test xem api chạy không");
    }

    @GetMapping("/get-session")
    public Map<String, Object> getSession(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("user") != null) {
            response.put("loggedIn", true);
            response.put("user", session.getAttribute("user")); // Trả về thông tin user nếu cần
        } else {
            response.put("loggedIn", false);
        }
        return response;
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String passwordHash, HttpSession session) {
        try {
            Optional<User> userLogin = userService.getLoginWithEmailAndPasswordHash(email, passwordHash);
    
            if (userLogin.isPresent()) {
                session.setAttribute("user", userLogin.get());
                return ResponseEntity.ok(userLogin.get());
            } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
