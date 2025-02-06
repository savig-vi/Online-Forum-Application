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
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID")
    private int commentID;

    @ManyToOne
    @JoinColumn(name = "PostID", nullable = false)
    private Post post; // Quan hệ với Post (Bài viết được bình luận)

    @ManyToOne
    @JoinColumn(name = "AuthorID", nullable = false)
    private User author; // Quan hệ với User (Người bình luận)

    @Column(name = "Content", nullable = false)
    private String content; // Nội dung bình luận

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CommentDate", nullable = false)
    private Date commentDate; // Thời gian bình luận

    @Column(name = "IsActive", nullable = false)
    private boolean isActive; // Trạng thái bình luận (đang hoạt động hay bị khóa)
}
