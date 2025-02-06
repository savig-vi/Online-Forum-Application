package com.vitaliy.forum.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.services.service.UserService;

@RestController
@RequestMapping("/users")
public class Register {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Gọi service để thực hiện đăng ký
        User registeredUser = userService.registerUser(user);
        // Trả về kết quả (người dùng đã được đăng ký)
        return ResponseEntity.ok(registeredUser);
    }
}
