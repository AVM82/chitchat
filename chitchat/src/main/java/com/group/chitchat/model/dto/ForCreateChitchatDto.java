package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Levels;
import jakarta.validation.constraints.Future;
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

  private String chatHeader;
  private Integer categoryId;
  private String description;
  private String languageId;
  private Levels level;
  private int capacity;
  @Future(message = "{v.future_date}")
  private LocalDateTime date;
}
