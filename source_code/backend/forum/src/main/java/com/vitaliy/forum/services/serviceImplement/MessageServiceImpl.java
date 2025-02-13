package com.vitaliy.forum.services.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitaliy.forum.entity.Message;
import com.vitaliy.forum.entity.User;
import com.vitaliy.forum.repository.MessageRepository;
import com.vitaliy.forum.repository.UserRepository;
import com.vitaliy.forum.services.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả tin nhắn gửi từ người dùng đến người nhận
    public List<Message> getMessagesBySender(int senderId) {
        return messageRepository.findBySender_UserId(senderId);
    }

    // Lấy tất cả tin nhắn gửi đến người dùng
    public List<Message> getMessagesByReceiver(int receiverId) {
        return messageRepository.findByReceiver_UserId(receiverId);
    }

    // Lấy tất cả tin nhắn giữa hai người dùng
    public List<Message> getMessagesBetweenUsers(int user1Id, int user2Id) {
        return messageRepository.findBySender_UserIdAndReceiver_UserId(user1Id, user2Id);
    }

    // Lấy tin nhắn theo ID
    public Message getMessageById(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElseThrow(() -> new RuntimeException("Message not found for id: " + messageId));
    }

    // Gửi một tin nhắn mới
    public Message sendMessage(int senderId, int receiverId, String content) {
        Message message = new Message();
        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);

        message.setSender(sender);      // Gán người gửi
        message.setReceiver(receiver);  // Gán người nhận
        message.setContent(content);                // Gán nội dung tin nhắn
        message.setSentDate(new java.util.Date());   // Gán thời gian gửi
        message.setRead(false);                   // Đánh dấu tin nhắn là chưa đọc
        return messageRepository.save(message);     // Lưu tin nhắn vào cơ sở dữ liệu
    }

    // Đánh dấu tin nhắn là đã đọc
    public Message markAsRead(int messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found for id: " + messageId));
        message.setRead(true);
        return messageRepository.save(message);
    }

    // Xóa tin nhắn theo ID
    public void deleteMessage(int messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found for id: " + messageId));
        messageRepository.delete(message);
    }

    // Kiểm tra xem tin nhắn có tồn tại không
    public boolean isMessageExist(int messageId) {
        return messageRepository.existsById(messageId);
    }

    // Xóa tất cả tin nhắn giữa hai người dùng
    public void deleteMessagesBetweenUsers(int user1Id, int user2Id) {
        List<Message> messages = messageRepository.findBySender_UserIdAndReceiver_UserId(user1Id, user2Id);
        messageRepository.deleteAll(messages);
    }
}
