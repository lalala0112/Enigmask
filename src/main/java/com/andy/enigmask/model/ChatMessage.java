package com.andy.enigmask.model;

import lombok.Data;

@Data
public class ChatMessage {
    private String id;             // Unique identifier for the message
    private String senderId;       // Sender's user ID
    private String recipientId;    // Recipient's user ID
    private String content;        // Encrypted content
    private String timestamp;      // Timestamp of the message
    private MessageStatus status;  // Status of the message (PENDING, SENT, DELIVERED, READ)
}
