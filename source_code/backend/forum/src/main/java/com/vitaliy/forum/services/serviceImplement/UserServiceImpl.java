package com.vitaliy.forum.services.serviceImplement;

import java.util.Date;
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

    public Optional<User> getLoginWithEmailAndPasswordHash(String email, String passwordHash) {
        Optional<User> user = userRepository.findByEmailAndPasswordHash(email, passwordHash);
        return user;
    }
    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy người dùng theo ID
    public User getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
    }

    // Tạo mới người dùng
    public User createUser(User user) {
        user.setRegistrationDate(null);
        user.setFacebookId(null);
        user.setRegistrationDate(new Date()); // Thời gian đăng ký
        user.setUserRole(false); // Mặc định user có role người dùng thường
        user.setActive(true); // Mặc định tài khoản là active
        return userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }

    // Cập nhật thông tin người dùng
    public User updateUser(int userId, String fullName, String phoneNumber, String address, String email, String passwordHash) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));

        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        return userRepository.save(user); // Lưu người dùng đã cập nhật
    }

    // Xóa người dùng
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
        userRepository.delete(user); // Xóa người dùng khỏi cơ sở dữ liệu
    }

    // Thay đổi trạng thái người dùng (kích hoạt / vô hiệu hóa)
    public User toggleUserStatus(int userId, boolean isActive) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));

        user.setActive(isActive);
        return userRepository.save(user); // Lưu trạng thái hoạt động đã thay đổi
    }

    // Kiểm tra người dùng có tồn tại với username hay email
    public boolean userExists(String userName, String email) {
        return userRepository.existsByUserNameOrEmail(userName, email);
    }

    // Lấy người dùng theo username (giả sử username là duy nhất)
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("User not found for username: " + userName));
    }
}
