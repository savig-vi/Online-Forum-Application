package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderId(User senderId);
    List<Message> findByReceiverId(User receiverId);
    List<Message> findByIsRead(Boolean isRead);
    List<Message> findByReceiverIdAndIsRead(User rereceiver, Boolean isRead);
}
