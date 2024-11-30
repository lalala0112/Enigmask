package com.andy.enigmask.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotification {

    private String id;              // Primary key
    private String senderId;
    private String recipientId;
    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

}
