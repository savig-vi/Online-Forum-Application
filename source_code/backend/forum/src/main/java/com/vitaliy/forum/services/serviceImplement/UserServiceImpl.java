package com.vitaliy.forum.services.serviceImplement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        user.setRegistrationDate(timestamp);
        user.setUserRole(false);
        user.setActive(true);
        user.setFacebookId(null);
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
        return userRepository.findByUserName(username);
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

    @Override
    public boolean checkInfoLogin(String email, String passwordHash) {
        Optional<User> optionalUser = userRepository.findByEmailAndPasswordHash(email, passwordHash);
        if (optionalUser.isPresent()) {
            System.out.println("IsPresent - FIND BY EMAIL VA PASSWORD: " + optionalUser.get());
            return true;
        }
        System.out.println("Khong ton tai " + email + " va " + passwordHash);
        return false; // Đăng nhập thất bại
    }
}
