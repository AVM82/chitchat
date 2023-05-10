package com.group.chitchat.service;

import com.group.chitchat.model.Role;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.UserForResponseDto;

public class UserDtoService {

  private UserDtoService() {
  }

  /**
   * Create dto with data of users profile from entity user.
   *
   * @param user entity user.
   * @return dto with data of users profile.
   */
  public static UserForResponseDto profileDtoFromUser(User user) {
    return UserForResponseDto.builder()
        .userName(user.getUsername())
        .roles(user.getRoles().stream().map(Role::getName).toList())
        .email(user.getEmail())
        .avatar(user.getUserData().getAvatar())
        .build();
  }

  /**
   * Create dto with detailed data of users profile from entity user.
   *
   * @param user entity user.
   * @return dto with data of users profile.
   */
  public static UserForResponseDto profileDetailsDtoFromUser(User user) {
    return UserForResponseDto.builder()
        .userName(user.getUsername())
        .roles(user.getRoles().stream().map(Role::getName).toList())
        .email(user.getEmail())
        .avatar(user.getUserData().getAvatar())
        .firstname(user.getUserData().getFirstName())
        .lastname(user.getUserData().getLastName())
        .gender(user.getUserData().getGender())
        .dob(user.getUserData().getDob())
        .nativeLanguage(user.getUserData().getNativeLanguage() != null
            ? user.getUserData().getNativeLanguage().getName() : null)
        .build();
  }
}
