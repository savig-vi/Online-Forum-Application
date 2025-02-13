package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Message;

public interface MessageService {
    List<Message> getMessagesBySender(int senderId);
    List<Message> getMessagesByReceiver(int receiverId);
    List<Message> getMessagesBetweenUsers(int user1Id, int user2Id);
    Message getMessageById(int messageId);
    Message sendMessage(int senderId, int receiverId, String content);
    Message markAsRead(int messageId);
    void deleteMessage(int messageId);
    boolean isMessageExist(int messageId);
    void deleteMessagesBetweenUsers(int user1Id, int user2Id);
}
