package com.andy.enigmask.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_notifications") // Table name in PostgreSQL
public class ChatNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id; // Changed to Long for compatibility with relational database primary keys

    @Column(name = "sender_id", nullable = false)
    private String senderId; // ID of the sender

    @Column(name = "recipient_id", nullable = false)
    private String recipientId; // ID of the recipient

    @Column(name = "content", nullable = false)
    private String content; // Message content

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Automatically set timestamp

}
