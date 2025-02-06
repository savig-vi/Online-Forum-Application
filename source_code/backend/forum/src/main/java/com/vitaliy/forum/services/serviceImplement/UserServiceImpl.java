package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.UserRepository;
import com.vitaliy.forum.services.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Xử lý việc check thông tin db trong đây, hiện tại chưa làm
     * cái check mail trùng
     */
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void activateUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setActive(true);
            updateUser(user);
        }
    }

    @Override
    public void deactivateUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setActive(false);
            updateUser(user);
        }
    }
}
