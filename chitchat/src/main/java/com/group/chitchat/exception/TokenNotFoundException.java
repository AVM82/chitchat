package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class TokenNotFoundException extends RuntimeException {
  /**
   * Exceptions are cases where the refresh token is not found in the database.
   *
   */
  public TokenNotFoundException() {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.not_exist",
                Locale.getDefault()), "token"));
  }
}
