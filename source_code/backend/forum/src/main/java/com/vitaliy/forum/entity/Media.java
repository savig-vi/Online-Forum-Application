package com.vitaliy.forum.entity;

import java.util.Date;

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

@Entity
@Data
@Table(name = "Media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaID")
    private int mediaID;

    @ManyToOne
    @JoinColumn(name = "PostID")
    private Post post; // Bài viết chứa media (nếu có)

    @ManyToOne
    @JoinColumn(name = "CommentID")
    private Comment comment; // Bình luận chứa media (nếu có)

    @Column(name = "FileURL", nullable = false)
    private String fileURL; // Đường dẫn đến file

    @Enumerated(EnumType.STRING)
    @Column(name = "FileType", nullable = false)
    private FileType fileType; // Loại tệp (Hình ảnh hoặc Video)

    @ManyToOne
    @JoinColumn(name = "UploadedBy", nullable = false)
    private User uploadedBy; // Người tải lên

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UploadDate", nullable = false)
    private Date uploadDate; // Thời gian tải lên

    public enum FileType {
        IMAGE, VIDEO;
    }
}
