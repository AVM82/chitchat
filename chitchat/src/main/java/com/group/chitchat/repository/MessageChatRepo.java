package com.group.chitchat.repository;

import com.group.chitchat.model.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatRepo extends JpaRepository<ChatMessage, Long> {

  List<ChatMessage> findAllByChitchatId(Long chitchatId);
}
