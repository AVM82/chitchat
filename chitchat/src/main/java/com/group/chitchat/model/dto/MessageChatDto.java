package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Subscription;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageChatDto {

  private Long id;
  private Long authorId;
  private Long chitchatId;
  private String message;
  private LocalDateTime createdTime;
  private Subscription subscriptionType;

  /**
   * Constructor without id.
   * @param authorId author id number.
   * @param chitchatId Chitchat id number.
   * @param message message text.
   * @param createdTime the date the message was created.
   * @param subscriptionType user subscription type.
   */
  public MessageChatDto(Long authorId, Long chitchatId, String message, LocalDateTime createdTime,
      Subscription subscriptionType) {
    this.authorId = authorId;
    this.chitchatId = chitchatId;
    this.message = message;
    this.createdTime = createdTime;
    this.subscriptionType = subscriptionType;
  }
}
