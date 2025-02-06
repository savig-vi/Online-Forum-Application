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
@Table(name = "Follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FollowID")
    private int followID;

    @ManyToOne
    @JoinColumn(name = "FollowerID", nullable = false)
    private User follower; // Người theo dõi

    @ManyToOne
    @JoinColumn(name = "FollowedID", nullable = false)
    private User followed; // Người được theo dõi

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FollowDate", nullable = false)
    private Date followDate; // Thời gian theo dõi
}
