package com.group.chitchat.model.dto;

import java.time.LocalDateTime;
import java.util.List;
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
public class ChitchatForResponseDto {

  private long id;
  private String chatName;
  private String authorName;
  private String categoryName;
  private String description;
  private String languageName;
  private String level;
  private int capacity;
  private LocalDateTime date;
  private List<String> usersInChitchat;
}
