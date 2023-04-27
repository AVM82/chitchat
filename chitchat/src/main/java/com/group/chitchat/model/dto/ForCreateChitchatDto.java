package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Levels;
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
public class ForCreateChitchatDto {

  private String chatName;
  private String categoryName;
  private String description;
  private String languageName;
  private Levels level;
  private int capacity;
  private LocalDateTime date;
}
