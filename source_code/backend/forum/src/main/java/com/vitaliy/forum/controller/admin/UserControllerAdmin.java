package com.vitaliy.forum.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.dto.UserUpdateActiveDTO;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.services.service.UserService;

@RestController
@RequestMapping("/api/admin/user")
public class UserControllerAdmin {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    } 

    @PatchMapping("/updateActive")
    public void updateActive(@RequestBody UserUpdateActiveDTO userUpdateActiveDTO) {
        userService.toggleUserStatus(userUpdateActiveDTO.getUserId(), userUpdateActiveDTO.isActive());
    } 
}
