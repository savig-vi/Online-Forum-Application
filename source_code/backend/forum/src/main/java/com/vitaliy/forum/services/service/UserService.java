package com.vitaliy.forum.services.service;

import java.util.List;
import java.util.Optional;

import com.vitaliy.forum.entity.User;

public interface UserService {
    User registerUser(User user);
    User getUserById(int id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(int id);
    void activateUser(int id);
    void deactivateUser(int id);
    boolean checkInfoLogin(String email, String passwordHash);
}