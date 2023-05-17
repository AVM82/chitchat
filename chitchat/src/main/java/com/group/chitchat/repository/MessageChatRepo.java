package com.group.chitchat.repository;

import com.group.chitchat.model.MessageChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatRepo extends JpaRepository<MessageChat, Long> {
  List<MessageChat> findAllByChitchatId(Long chitchatId);
}
