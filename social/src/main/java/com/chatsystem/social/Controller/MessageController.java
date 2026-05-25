package com.chatsystem.social.Controller;

import com.chatsystem.social.DTO.MessageResponse;
import com.chatsystem.social.DTO.SendMessageRequest;
import com.chatsystem.social.Service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Send a message
    @PostMapping("/send")
    public MessageResponse sendMessage(@RequestBody SendMessageRequest request) {
        return messageService.sendMessage(request);
    }

    // Get conversation with a specific user
    @GetMapping("/conversation/{userId}")
    public List<MessageResponse> getConversation(@PathVariable String userId) {
        return messageService.getConversation(userId);
    }

    // Mark messages as read when chat window is opened
    @PutMapping("/read/{userId}")
    public String markAsRead(@PathVariable String userId) {
        messageService.markAsRead(userId);
        return "Messages marked as read";
    }

    // Get total unread message count
    @GetMapping("/unread/count")
    public int getUnreadCount() {
        return messageService.getUnreadCount();
    }
}