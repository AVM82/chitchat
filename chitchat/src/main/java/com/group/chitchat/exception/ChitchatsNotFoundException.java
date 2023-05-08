package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class ChitchatsNotFoundException extends RuntimeException {

  /**
   * Exceptions are cases where the chitchat is not found in the database.
   *
   * @param id of chitchat.
   */
  public ChitchatsNotFoundException(Long id) {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.chat_not_found",
                Locale.getDefault()),
        id));
  }

  /**
   * Exceptions are cases where the chitchats of user are not found in the database.
   */
  public ChitchatsNotFoundException() {

    super(new BundleService().getMessForLocale(
        "m.not_have_chats", Locale.getDefault()));
  }
}
