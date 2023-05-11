package com.group.chitchat.service.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BundleService implements BundlesService {

  public static final String MESSAGES = "messages";

  @Override
  public String getMessForLocale(String keyMessage, Locale locale) {
    return ResourceBundle.getBundle(MESSAGES, locale).getString(keyMessage);
  }

  @Override
  public String getExceptionMessForLocale(HttpStatus httpStatus, Locale locale) {

    return ResourceBundle.getBundle(MESSAGES, locale)
        .getString("e." + httpStatus.getReasonPhrase()).replace(" ", "_");
  }
}
