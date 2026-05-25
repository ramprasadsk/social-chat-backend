package com.chatsystem.social.Repository;

import com.chatsystem.social.Entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    // Get full conversation between two users
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySentAtAsc(
        String senderId, String receiverId,
        String receiverId2, String senderId2
    );

    // Get unread messages for a user
    List<Message> findByReceiverIdAndReadFalse(String receiverId);
}