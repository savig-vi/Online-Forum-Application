package com.vitaliy.forum.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int categoryID;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "Description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CreatedBy", nullable = false)
    private User createdBy; // Quan hệ với User (Quản trị viên tạo thể loại)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate", nullable = false)
    private Date createdDate;
    
}
