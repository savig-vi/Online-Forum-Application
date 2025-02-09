package com.vitaliy.forum.controller.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/common")
public class LogoutController {
    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        session.invalidate(); // Xóa session
        response.put("status", "success");
        response.put("message", "Đăng xuất thành công!");
        return response;
    }
}
