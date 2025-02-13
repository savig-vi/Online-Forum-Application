package com.vitaliy.forum.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
    @JoinColumn(name = "Post", nullable = false)
    @JsonBackReference
    private Post post; // Quan hệ với Post (Bài viết được bình luận)

    @ManyToOne
    @JoinColumn(name = "Commenter", nullable = false)
    private User commenter; // Quan hệ với User (Người bình luận)

    @Column(name = "Content", nullable = false, columnDefinition = "TEXT")
    @Lob
    private String content; // Nội dung bình luận

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CommentDate")
    private Date commentDate; // Thời gian bình luận

    @Column(name = "IsActive")
    private boolean isActive;
}
