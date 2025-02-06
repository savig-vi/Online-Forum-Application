package com.vitaliy.forum.entity;

import java.util.Date;

import com.vitaliy.forum.entity._enum.Visibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID")
    private int postID;

    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    @Column(name = "Content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category; // Quan hệ với Category (Thể loại bài viết)

    @ManyToOne
    @JoinColumn(name = "AuthorID", nullable = false)
    private User author; // Quan hệ với User (Người tạo bài viết)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PostDate", nullable = false)
    private Date postDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Visibility", nullable = false)
    private Visibility visibility; // Chế độ hiển thị bài viết

    @Column(name = "IsActive", nullable = false)
    private boolean isActive; // Trạng thái bài viết
}
