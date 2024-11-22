package com.andy.enigmask.repository;

import com.andy.enigmask.model.ChatMessage;
import com.andy.enigmask.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage c SET c.status = :newStatus WHERE c.senderId = :senderId AND c.recipientId = :recipientId AND c.status = :currentStatus")
    void updateStatus(String senderId, String recipientId, MessageStatus currentStatus, MessageStatus newStatus);

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status
    );

    List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);

    List<ChatMessage> findByRecipientIdAndStatus(
            String recipientId, MessageStatus status
    );

}
