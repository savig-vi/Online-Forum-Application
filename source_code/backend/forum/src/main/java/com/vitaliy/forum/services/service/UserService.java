package com.vitaliy.forum.services.service;

import java.util.List;
import java.util.Optional;

import com.vitaliy.forum.entity.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int userId);
    User createUser(User user);
    User updateUser(int userId, String fullName, String phoneNumber, String address, String email);
    void deleteUser(int userId);
    User toggleUserStatus(int userId, boolean isActive);
    boolean userExists(String userName, String email);
    Optional<User> getLoginWithEmailAndPasswordHash(String email, String passwordHash);
}