package com.vitaliy.forum.entity;

import java.util.Date;

import javax.management.relation.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int userID;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "PasswordHash", length = 255)
    private String passwordHash;

    @Column(name = "Email", unique = true, length = 100)
    private String email;

    @Column(name = "FullName", length = 50)
    private String fullName;

    @Column(name = "PhoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RegistrationDate", nullable = false)
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastLogin")
    private Date lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;

    @Column(name = "IsActive", nullable = false)
    private boolean isActive;

    @Column(name = "GoogleID", length = 255)
    private String googleID;

    @Column(name = "FacebookID", length = 255)
    private String facebookID;
}
