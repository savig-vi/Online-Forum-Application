package com.vitaliy.forum.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitaliy.forum.dto.UserUpdateInformationDTO;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.services.service.UserService;

@RestController
@CrossOrigin(origins = "https://localhost:5501")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.createUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PatchMapping("/updateInformation")
    public ResponseEntity<User> updateInformation(@RequestBody UserUpdateInformationDTO userDTO) {
        int userId = userDTO.getUserId();
        String fullName = userDTO.getFullName();
        String address = (userDTO.getAddress());
        String phoneNumber = userDTO.getPhoneNumber();
        String passwordHash = userDTO.getNewPasswordHash();
        String email = userDTO.getEmail();
        return ResponseEntity.ok(userService.updateUser(userId, fullName, phoneNumber, address, email, passwordHash));

    }
}
