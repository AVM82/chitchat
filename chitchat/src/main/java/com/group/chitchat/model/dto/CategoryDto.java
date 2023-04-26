package com.group.chitchat.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

  int id;
  @NotNull
  String categoryName;
  int priority;

  @Override
  public String toString() {
    return "CategoryDto{"
        + "id=" + id
        + ", categoryName='" + categoryName + '\''
        + ", priority=" + priority
        + '}';
  }
}
