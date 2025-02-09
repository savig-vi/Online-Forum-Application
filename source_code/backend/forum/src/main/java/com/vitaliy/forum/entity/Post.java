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
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostId")
    private int postId;

    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    @Column(name = "Content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category categoryId; // Quan hệ với Category (Thể loại bài viết)

    @ManyToOne
    @JoinColumn(name = "AuthorId", nullable = false)
    private User authorId; // Quan hệ với User (Người tạo bài viết)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PostDate")
    private Date postDate;

    @Column(name = "Visibility")
    private boolean visibility; // Chế độ hiển thị bài viết

    @Column(name = "IsActive")
    private boolean isActive; // Trạng thái bài viết
}
