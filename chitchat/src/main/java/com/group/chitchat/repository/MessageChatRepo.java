package com.group.chitchat.repository;

import com.group.chitchat.model.MessageChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatRepo extends JpaRepository<MessageChat, Long> {

}
