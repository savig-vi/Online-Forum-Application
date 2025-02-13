package com.vitaliy.forum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailAndPasswordHash(String email, String passwordHash);
    User findByEmail(String email);
    List<User> findByIsActive(boolean isActive);
    @Modifying
    @Query("UPDATE User u SET u.isActive = ?1 WHERE u.userId = ?2")
    int updateUserActiveStatus(boolean isActive, int userId);

    // @Modifying
    // @Transactional
    // @Query("UPDATE User u SET u.fullName = :fullName, u.phoneNumber = :phoneNumber, u.email = :email, u.address = :address, u.passwordHash = :passwordHash WHERE u.userId = :userId")
    // void updateUserInfo(@Param("userId") Long userId, 
    //                     @Param("fullName") String fullName, 
    //                     @Param("phoneNumber") String phoneNumber, 
    //                     @Param("email") String email,
    //                     @Param("address") String address, 
    //                     @Param("passwordHash") String passwordHash);
    // boolean existsByEmail(String email);
    boolean existsByUserNameOrEmail(String userName, String email);
} 
