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
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentId")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "PostId", nullable = false)
    private Post postId; // Quan hệ với Post (Bài viết được bình luận)

    @ManyToOne
    @JoinColumn(name = "AuthorId", nullable = false)
    private User authorId; // Quan hệ với User (Người bình luận)

    @Column(name = "Content", nullable = false)
    private String content; // Nội dung bình luận

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CommentDate")
    private Date commentDate; // Thời gian bình luận

    @Column(name = "IsActive")
    private boolean isActive; // Trạng thái bình luận (đang hoạt động hay bị khóa)
}
