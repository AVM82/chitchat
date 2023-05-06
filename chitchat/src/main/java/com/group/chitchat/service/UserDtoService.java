package com.group.chitchat.service;

import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.UserDto;

public class UserDtoService {

  private UserDtoService() {
  }

  public static UserDto userForProfile(User user) {
    return UserDto.builder()
        .userName(user.getUsername())
        .role(user.getRoles().iterator().next().getName())
        .email(user.getEmail())
        .avatar(user.getUserData().getAvatar())
        .build();
  }

  public static UserDto userForChange(User user) {
    return UserDto.builder()
        .email(user.getEmail())
        .firstname(user.getUserData().getFirstName())
        .lastname(user.getUserData().getLastName())
        .gender(user.getUserData().getGender())
        .dob(user.getUserData().getDob())
        .build();
  }
}
