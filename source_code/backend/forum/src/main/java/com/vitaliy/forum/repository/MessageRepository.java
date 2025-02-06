package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
    List<Message> findByIsRead(Boolean isRead);
    List<Message> findByReceiverAndIsRead(User rereceiver, Boolean isRead);
}
