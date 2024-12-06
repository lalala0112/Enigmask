package com.andy.enigmask.repository;

import com.andy.enigmask.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    // Find messages by chatId
    List<ChatMessage> findByChatId(String chatId);

}
