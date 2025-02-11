package com.vitaliy.forum.services.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.MessageRepository;
import com.vitaliy.forum.services.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
@Autowired
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Message> getMessagesBySender(User senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    @Override
    public List<Message> getMessagesByReceiver(User receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    @Override
    public List<Message> getUnreadMessages(User receiverId) {
        return messageRepository.findByReceiverIdAndIsRead(receiverId, false);
    }

    @Override
    public void markMessageAsRead(int messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            message.setRead(true);
            messageRepository.save(message);
        }
    }

    @Override
    public void deleteMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }
}
