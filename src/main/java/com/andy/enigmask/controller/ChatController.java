package com.andy.enigmask.controller;

import com.andy.enigmask.model.ChatMessage;
import com.andy.enigmask.model.MessageStatus;
import com.andy.enigmask.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.SENT);
        ChatMessage savedMessage = chatMessageService.saveMessage(chatMessage);

        // Send the message to the recipient
        messagingTemplate.convertAndSendToUser(savedMessage.getRecipientId(), "/queue/chat", savedMessage);
    }

    @MessageMapping("/chat.markAsRead")
    public void markMessagesAsRead(String senderId, String recipientId) {
        chatMessageService.markMessagesAsRead(senderId, recipientId);
        messagingTemplate.convertAndSendToUser(recipientId, "/queue/notifications", "Messages marked as read");
    }

    // Retrieve all messages between two users
    @MessageMapping("/chat.getMessages")
    public void getMessages(String senderId, String recipientId) {
        List<ChatMessage> messages = chatMessageService.getMessagesBetweenUsers(senderId, recipientId);
        messagingTemplate.convertAndSendToUser(senderId, "/queue/chat", messages);
    }

    // Get the count of unread messages
    @MessageMapping("/chat.getUnreadCount")
    public void getUnreadMessageCount(String senderId, String recipientId) {
        long unreadCount = chatMessageService.countUnreadMessages(senderId, recipientId);
        messagingTemplate.convertAndSendToUser(recipientId, "/queue/unreadCount", unreadCount);
    }
}
