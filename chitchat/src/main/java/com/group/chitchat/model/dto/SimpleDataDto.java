package com.group.chitchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleDataDto<T> {

  private T value;
}