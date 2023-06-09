package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Levels;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "{notNull}")
  @NotEmpty(message = "{notEmpty}")
  private String chatHeader;
  private Integer categoryId;
  private String description;
  private String languageId;
  private Levels level;
  private int capacity;
  @Future(message = "{future}")
  private LocalDateTime date;
}
