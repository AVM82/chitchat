package com.group.chitchat.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopicDto {

  int id;
  @NotNull
  String topicName;
  int priority;

  @Override
  public String toString() {
    return "TopicDto{"
        + "id=" + id
        + ", topicName='" + topicName + '\''
        + ", priority=" + priority
        + '}';
  }
}
