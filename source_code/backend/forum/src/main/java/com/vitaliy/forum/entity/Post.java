package com.vitaliy.forum.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(name = "Content", nullable = false, columnDefinition = "TEXT")
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "Category", nullable = false)
    @JsonBackReference
    private Category category; // Quan hệ với Category (Thể loại bài viết)

    @ManyToOne
    @JoinColumn(name = "Author", nullable = false)
    private User author; // Quan hệ với User (Người tạo bài viết)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PostDate")
    private Date postDate;

    @Column(name = "Visibility")
    private boolean visibility; // Chế độ hiển thị bài viết

    @Column(name = "IsActive")
    private boolean isActive; // Trạng thái bài viết

    // Thêm liên kết với Comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();
}
