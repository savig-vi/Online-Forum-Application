package com.vitaliy.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySender(User sender);
    List<Message> findBySender_UserId(int senderId);
    List<Message> findByReceiver(User receiver);
    List<Message> findByReceiver_UserId(int receiverId);
    List<Message> findBySender_UserIdAndReceiver_UserId(int senderId, int receiverId);
    List<Message> findBySenderAndReceiver(User sender, User receiver);
    List<Message> findByIsReadFalse();
    List<Message> findByIsReadTrue();
    List<Message> findBySenderAndIsRead(User sender, boolean isRead);
    List<Message> findByReceiverAndIsRead(User receiver, boolean isRead);
    void deleteBySenderAndReceiver(User sender, User receiver);
}
