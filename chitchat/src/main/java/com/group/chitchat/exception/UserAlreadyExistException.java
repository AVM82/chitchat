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
        "Sorry_but_username_with_username", Locale.getDefault())
        + "\"" + username + "\"" + new ResourceBundleService().getMessForLocale(
        "already_exist._Try_another_username.", Locale.getDefault()));
  }
}
