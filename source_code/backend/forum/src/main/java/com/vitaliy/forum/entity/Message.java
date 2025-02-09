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
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageId")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "SenderID", nullable = false)
    private User senderId; // Người gửi tin nhắn

    @ManyToOne
    @JoinColumn(name = "ReceiverId", nullable = false)
    private User receiverId; // Người nhận tin nhắn

    @Column(name = "Content", nullable = false)
    private String content; // Nội dung tin nhắn

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SentDate")
    private Date sentDate; // Thời gian gửi tin nhắn

    @Column(name = "IsRead")
    private boolean isRead; // Đánh dấu tin nhắn đã đọc hay chưa
}
