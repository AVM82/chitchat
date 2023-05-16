package com.group.chitchat.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InternalChatMessageDto {
  private LocalDateTime createdAt;
  private String userName;
  private long chitchatId;
  private String message;
}
