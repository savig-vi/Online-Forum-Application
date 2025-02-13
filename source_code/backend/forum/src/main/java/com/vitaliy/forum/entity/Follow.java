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
@Table(name = "Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FollowId")
    private int followId;

    @ManyToOne
    @JoinColumn(name = "Follower", nullable = false)
    private User follower; // Người theo dõi

    @ManyToOne
    @JoinColumn(name = "Followed", nullable = false)
    private User followed; // Người được theo dõi

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FollowDate")
    private Date followDate; // Thời gian theo dõi
}
