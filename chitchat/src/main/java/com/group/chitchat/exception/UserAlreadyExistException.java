package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class UserAlreadyExistException extends RuntimeException {

  /**
   * Exception for cases when user trying to register with same info as user what have registered
   * before.
   *
   * @param username Name of user.
   */
  public UserAlreadyExistException(String username) {

    super(String.format(
        new BundleService()
            .getMessForLocale("m.user_already_exist",
                Locale.getDefault()),
        username));
  }

  /**
   * Exception for cases when user trying to add to chitchat, but he is already in chitchat.
   *
   * @param userId id of user.
   */
  public UserAlreadyExistException(Long userId) {
    super(String.format(
        new BundleService()
            .getMessForLocale("m.userId_already_exist",
                Locale.getDefault()),
        userId));

  }
}
