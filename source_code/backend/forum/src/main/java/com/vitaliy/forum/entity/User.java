package com.vitaliy.forum.entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int userId;

    @Column(name = "UserName", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "PasswordHash", length = 255)
    private String passwordHash;

    @Column(name = "Email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "FullName", length = 50, nullable = false)
    private String fullName;

    @Column(name = "PhoneNumber", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "Address", nullable = false)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RegistrationDate")
    private Date registrationDate;


    @Column(name = "UserRole")
    private boolean userRole;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "FacebookId")
    private String facebookId;

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", passwordHash=" + passwordHash + ", email="
                + email + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", address=" + address
                + ", registrationDate=" + registrationDate + ", userRole=" + userRole + ", isActive=" + isActive
                + ", facebookId=" + facebookId + "]";
    }
}
