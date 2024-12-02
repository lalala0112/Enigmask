package com.andy.enigmask.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id;                    // Unique identifier for the message

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "sender_id", nullable = false)
    private String senderId;            // Sender's user ID

    @Column(name = "recipient_id", nullable = false)
    private String recipientId;         // Recipient's user ID

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;             // Message content TODO: Encrypting content

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;    // Timestamp of the message

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus status;       // Status of the message (PENDING, SENT, DELIVERED, READ)
}
