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

@Entity
@Data
@Table(name = "Media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaId")
    private int mediaId;

    @ManyToOne
    @JoinColumn(name = "PostId")
    private Post postId; // Bài viết chứa media (nếu có)

    @ManyToOne
    @JoinColumn(name = "CommentId")
    private Comment commentId; // Bình luận chứa media (nếu có)

    @Column(name = "FileURL", nullable = false)
    private String fileURL; // Đường dẫn đến file

    @Column(name = "FileType")
    private Boolean fileType; // Loại tệp (Hình ảnh hoặc Video)

    @ManyToOne
    @JoinColumn(name = "UploadedById", nullable = false)
    private User uploadedById; // Người tải lên

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UploadDate")
    private Date uploadDate; // Thời gian tải lên
}
