package com.group.chitchat.exception;

import com.group.chitchat.service.ResourceBundleService;
import java.util.Locale;


public class UserNotFoundException extends RuntimeException {

  /**
   * The exception is when the user tries to enter the site with a username that is not found.
   *
   * @param username The username.
   */
  public UserNotFoundException(String username) {
    super(new ResourceBundleService().getMessForLocale(
        "User_with_username", Locale.getDefault())
        + " \"" + username + "\" " + new ResourceBundleService().getMessForLocale(
        "not_found.", Locale.getDefault()));
  }
}
