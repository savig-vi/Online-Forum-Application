package com.vitaliy.forum.services.service;

import java.util.List;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;

public interface MessageService {
    Message saveMessage(Message message);
    Message getMessageById(int id);
    List<Message> getMessagesBySender(User sender);
    List<Message> getMessagesByReceiver(User receiver);
    List<Message> getUnreadMessages(User receiver);
    void markMessageAsRead(int messageId);
    void deleteMessage(int messageId);
}
