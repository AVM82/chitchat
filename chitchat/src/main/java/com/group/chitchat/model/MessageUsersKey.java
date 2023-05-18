package com.group.chitchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MessageUsersKey implements Serializable {

  @Column(name = "messages_id")
  Long messagesId;

  @Column(name = "user_id")
  Long userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageUsersKey that = (MessageUsersKey) o;
    return Objects.equals(messagesId, that.messagesId) && Objects.equals(userId,
        that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messagesId, userId);
  }
}
