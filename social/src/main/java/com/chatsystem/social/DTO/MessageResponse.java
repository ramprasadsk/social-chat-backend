package com.chatsystem.social.DTO;

import java.time.LocalDateTime;

public class MessageResponse {

    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime sentAt;
    private boolean delivered;
    private boolean read;

    public MessageResponse(String id, String senderId, String receiverId,
                           String content, LocalDateTime sentAt,
                           boolean delivered, boolean read) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.delivered = delivered;
        this.read = read;
    }

    public String getId() { return id; }
    public String getSenderId() { return senderId; }
    public String getReceiverId() { return receiverId; }
    public String getContent() { return content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public boolean isDelivered() { return delivered; }
    public boolean isRead() { return read; }
}