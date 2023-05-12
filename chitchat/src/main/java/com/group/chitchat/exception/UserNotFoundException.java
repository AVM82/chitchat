package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;


public class UserNotFoundException extends RuntimeException {

  /**
   * The exception is when the user tries to enter the site with a username that is not found.
   *
   * @param username The username.
   */
  public UserNotFoundException(String username) {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.user_name_not_found",
                Locale.getDefault()),
        username));
  }

  /**
   * The exception is when the user tries to enter the site with id that is not found.
   *
   * @param id username.
   */
  public UserNotFoundException(Long id) {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.user_id_not_found",
                Locale.getDefault()),
        id));
  }
}
