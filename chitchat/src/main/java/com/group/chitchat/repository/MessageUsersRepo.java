package com.group.chitchat.repository;

import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.model.dto.ChitchatUnreadCountDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Modifying
  @Query(value = "UPDATE message_users "
      + "SET read_status = true "
      + "WHERE messages_id in ("
      + "          SELECT message_users.messages_id FROM message_users "
      + "            JOIN users ON message_users.user_id = users.id "
      + "            JOIN messages ON message_users.messages_id = messages.id "
      + "          WHERE message_users.read_status = false "
      + "and messages.chitchat_id = ?1 and users.username = ?2 and messages.created_time < ?3)",
      nativeQuery = true)
  int setMarkAsReadUserMessagesOfChitchat(
      Long chitchatId,
      String userName,
      LocalDateTime dateTime);
}
