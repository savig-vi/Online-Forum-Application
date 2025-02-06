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
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageID")
    private int messageID;

    @ManyToOne
    @JoinColumn(name = "SenderID", nullable = false)
    private User sender; // Người gửi tin nhắn

    @ManyToOne
    @JoinColumn(name = "ReceiverID", nullable = false)
    private User receiver; // Người nhận tin nhắn

    @Column(name = "Content", nullable = false)
    private String content; // Nội dung tin nhắn

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SentDate", nullable = false)
    private Date sentDate; // Thời gian gửi tin nhắn

    @Column(name = "IsRead", nullable = false)
    private boolean isRead; // Đánh dấu tin nhắn đã đọc hay chưa
}
