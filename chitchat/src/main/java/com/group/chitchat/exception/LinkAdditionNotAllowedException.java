package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class LinkAdditionNotAllowedException extends RuntimeException {

  /**
   * Exception for runtime exception when not author trying to add link.
   *
   * @param username name of current user.
   */
  public LinkAdditionNotAllowedException(String username) {
    super(String.format(
        new BundleService()
            .getMessForLocale("e.user_not_author",
                Locale.getDefault()),
        username));
  }
}
