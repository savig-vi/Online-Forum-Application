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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaId")
    private int mediaId;

    @ManyToOne
    @JoinColumn(name = "Post")
    private Post post; // Bài viết chứa media (nếu có)

    @ManyToOne
    @JoinColumn(name = "Comment")
    private Comment comment; // Bình luận chứa media (nếu có)

    @Column(name = "FileURL", nullable = false)
    private String fileURL; // Đường dẫn đến file

    @Column(name = "FileType")
    private boolean fileType; // Loại tệp (Hình ảnh hoặc Video)

    @ManyToOne
    @JoinColumn(name = "UploadedBy", nullable = false)
    private User uploadedBy; // Người tải lên

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UploadDate")
    private Date uploadDate; // Thời gian tải lên
}
