package com.group.chitchat.repository;

import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.model.dto.ChitchatUnreadCountDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageUsersRepo extends JpaRepository<MessageUsers, MessageUsersKey> {

  List<MessageUsers> findAllByUserUsernameAndReadStatusEquals(String userName, boolean status);

  @Query("SELECT COUNT(m) FROM MessageUsers AS m "
      + "WHERE m.user.username = ?1 and m.readStatus = false")
  Long totalCountUnreadUserMessages(String userName);

  @Query("SELECT new com.group.chitchat.model.dto.ChitchatUnreadCountDto("
      + "m.messages.chitchat.id, COUNT(m)) FROM MessageUsers AS m "
      + "WHERE m.user.username = ?1 and m.readStatus = false "
      + "GROUP BY m.messages.chitchat.id")
  List<ChitchatUnreadCountDto> findAllUnreadUserChitchats(String userName);

}
