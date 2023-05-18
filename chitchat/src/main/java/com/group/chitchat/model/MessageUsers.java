package com.group.chitchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@IdClass(MessageUsersKey.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message_users")
public class MessageUsers {

  @EmbeddedId
  MessageUsersKey id;

  @ManyToOne(targetEntity = MessageChat.class)
  @MapsId("messagesId")
  @JoinColumn(name = "messages_id", referencedColumnName = "id")
  private MessageChat messages;

  @ManyToOne(targetEntity = User.class)
  @MapsId("userId")
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "read_status")
  private Boolean readStatus;
}
