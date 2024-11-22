package com.andy.enigmask.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                      // Unique identifier for the message
    private String senderId;              // Sender's user ID
    private String recipientId;           // Recipient's user ID
    private String content;               // Encrypted content
    private LocalDateTime timestamp;      // Timestamp of the message

    @Enumerated(EnumType.STRING)
    private MessageStatus status;         // Status of the message (PENDING, SENT, DELIVERED, READ)
}
