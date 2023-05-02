package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.ResourceBundleService;
import java.util.Locale;

public class UserAlreadyExistException extends RuntimeException {

  /**
   * Exception for cases when user trying to register with same info as user what have registered
   * before.
   *
   * @param username Name of the user.
   */
  public UserAlreadyExistException(String username) {
    super(new ResourceBundleService().getMessForLocale(
            "exception.sorry_but_username", Locale.getDefault())
        + "\"" + username + "\"" + new ResourceBundleService().getMessForLocale(
            "exception.already_exist", Locale.getDefault()));
  }
}
