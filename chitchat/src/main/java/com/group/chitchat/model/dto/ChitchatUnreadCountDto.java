package com.group.chitchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChitchatUnreadCountDto {
  private long chitchatId;
  private long unreadCount;
}