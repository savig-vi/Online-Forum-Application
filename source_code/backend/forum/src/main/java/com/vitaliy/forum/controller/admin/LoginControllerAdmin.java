package com.vitaliy.forum.controller.admin;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class LoginControllerAdmin {

    @Autowired
    private UserRepository userRepository;
    // Đăng nhập
    @PostMapping("/login")
     public ResponseEntity<Map<String, Boolean>> adminLogin(@RequestParam String email, 
        @RequestParam String passwordHash, HttpSession session) {
        User user = userRepository.findByEmail(email);
        boolean isValid = (user != null && user.isUserRole() && user.getPasswordHash().equals(passwordHash));
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isValid);

        if (isValid) {
            session.setAttribute("admin", user); // Lưu session admin
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isLoggedIn(HttpSession session) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    } 
}
