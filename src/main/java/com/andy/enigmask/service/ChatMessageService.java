package com.andy.enigmask.service;

import com.andy.enigmask.model.ChatMessage;
import com.andy.enigmask.model.MessageStatus;
import com.andy.enigmask.repository.ChatMessageRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesBetweenUsers(String senderId, String recipientId) {
        return chatMessageRepository.findBySenderIdAndRecipientId(senderId, recipientId);
    }

    public long countUnreadMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.DELIVERED
        );
    }

    @Transactional
    public void markMessagesAsRead(String senderId, String recipientId) {
        chatMessageRepository.updateStatus(senderId, recipientId, MessageStatus.DELIVERED, MessageStatus.READ);
    }

    public List<ChatMessage> getUnreadMessages(String senderId, String recipientId) {
        return chatMessageRepository.findByRecipientIdAndStatus(
                recipientId, MessageStatus.DELIVERED
        );
    }

    public void updateMessageStatus(Long chatMessageId, MessageStatus status) {
        chatMessageRepository.findById(chatMessageId).ifPresent(message -> {
            message.setStatus(status);
            chatMessageRepository.save(message);
        });
    }

    public List<ChatMessage> getMessagesByRecipientAndStatus(String recipientId, MessageStatus status) {
        return chatMessageRepository.findByRecipientIdAndStatus(recipientId, status);
    }
}
