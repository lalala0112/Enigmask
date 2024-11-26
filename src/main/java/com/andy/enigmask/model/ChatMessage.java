package com.andy.enigmask.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessage {

    @Id
    private String id;                    // Unique identifier for the message
    private String chatId;                //
    private String senderId;              // Sender's user ID
    private String recipientId;           // Recipient's user ID
    private String content;               // Message content TODO: Encrypting content
    private LocalDateTime timestamp;      // Timestamp of the message
    private MessageStatus status;         // Status of the message (PENDING, SENT, DELIVERED, READ)

}
