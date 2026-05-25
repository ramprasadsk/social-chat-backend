package com.chatsystem.social.Service;

import com.chatsystem.social.DTO.MessageResponse;
import com.chatsystem.social.DTO.SendMessageRequest;
import com.chatsystem.social.Entity.Message;
import com.chatsystem.social.Entity.User;
import com.chatsystem.social.Repository.MessageRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private HttpSession session;

    // Send a message
    public MessageResponse sendMessage(SendMessageRequest request) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        Message message = new Message();
        message.setSenderId(currentUser.getId());
        message.setReceiverId(request.getReceiverId());
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setDelivered(false);
        message.setRead(false);

        Message saved = messageRepository.save(message);
        return toResponse(saved);
    }

    // Get conversation between logged-in user and another user
    public List<MessageResponse> getConversation(String otherUserId) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        String myId = currentUser.getId();

        List<Message> messages = messageRepository
            .findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySentAtAsc(
                myId, otherUserId,
                myId, otherUserId
            );

        return messages.stream()
                .map(this::toResponse)
                .toList();
    }

    // Mark all messages from otherUser to me as read
    public void markAsRead(String otherUserId) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<Message> unread = messageRepository
                .findByReceiverIdAndReadFalse(currentUser.getId());

        unread.stream()
            .filter(m -> m.getSenderId().equals(otherUserId))
            .forEach(m -> {
                m.setRead(true);
                m.setDelivered(true);
                messageRepository.save(m);
            });
    }

    // Get count of unread messages for logged-in user
    public int getUnreadCount() {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        return messageRepository.findByReceiverIdAndReadFalse(currentUser.getId()).size();
    }

    // Helper to convert Entity → DTO
    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getSentAt(),
                message.isDelivered(),
                message.isRead()
        );
    }
}