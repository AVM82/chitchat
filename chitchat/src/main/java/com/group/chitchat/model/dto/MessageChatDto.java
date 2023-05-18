package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Subscription;
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
  private String authorName;
  private Long chitchatId;
  private String message;
  private String createdTime;
  private Subscription subscriptionType;

  /**
   * Constructor without id.
   * @param authorName author name number.
   * @param chitchatId Chitchat id number.
   * @param message message text.
   * @param createdTime the date the message was created.
   * @param subscriptionType user subscription type.
   */
  public MessageChatDto(String authorName, Long chitchatId, String message, String createdTime,
      Subscription subscriptionType) {
    this.authorName = authorName;
    this.chitchatId = chitchatId;
    this.message = message;
    this.createdTime = createdTime;
    this.subscriptionType = subscriptionType;
  }
}
