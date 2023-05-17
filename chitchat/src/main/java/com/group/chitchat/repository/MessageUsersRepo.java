package com.group.chitchat.repository;

import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.MessageUsersKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageUsersRepo extends JpaRepository<MessageUsers, MessageUsersKey> {

}
