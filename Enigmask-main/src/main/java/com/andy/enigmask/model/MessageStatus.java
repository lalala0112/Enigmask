package com.andy.enigmask.model;

public enum MessageStatus {
    PENDING,     // Message is being processed or awaiting to be sent
    SENT,        // Message has been sent by the sender
    DELIVERED,   // Message has been delivered to the recipient
    READ         // Message has been read by the recipient
}
