package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class NotValidTranslationKeyException extends RuntimeException {

  /**
   * Exception for runtime exception when key of message not found.
   *
   * @param messageKey key of message for translate.
   */
  public NotValidTranslationKeyException(String messageKey) {
    super(String.format(
        new BundleService()
            .getMessForLocale("e.message_key_not_found",
                Locale.getDefault()),
        messageKey));
  }
}

