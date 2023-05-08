package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class ChitchatsNotFoundException extends RuntimeException {

  public ChitchatsNotFoundException(Long id) {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.chat_not_found",
                Locale.getDefault()),
        id));
  }

  public ChitchatsNotFoundException() {

    super(new BundleService().getMessForLocale(
        "m.not_have_chats", Locale.getDefault()));
  }
}
