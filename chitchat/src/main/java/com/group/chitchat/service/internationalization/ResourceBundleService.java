package com.group.chitchat.service.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResourceBundleService implements ResourcesBundleService {

  public static final String MESSAGES = "messages";
  private static final String MESSAGE_NOT_FOUND = "notExists";
  private static final String MESSAGE_FORBIDDEN = "forbidden";
  private static final String MESSAGE_CONFLICT = "conflict";
  private static final String MESSAGE_ERROR = "error";


  @Override
  public ResourceBundle getResourceBundle(Locale locale) {
    return ResourceBundle.getBundle(MESSAGES, locale);
  }

  @Override
  public String getMessForLocale(String keyMessage, Locale locale) {
    return ResourceBundle.getBundle(MESSAGES, locale).getString(keyMessage);
  }

  @Override
  public String getExceptionMessForLocale(HttpStatus httpStatus, Locale locale) {

    if (httpStatus == HttpStatus.CONFLICT) {
      return getMessForLocale(MESSAGE_CONFLICT, locale);
    }
    if (httpStatus == HttpStatus.NOT_FOUND) {
      return getMessForLocale(MESSAGE_NOT_FOUND, locale);
    }
    if (httpStatus == HttpStatus.FORBIDDEN) {
      return getMessForLocale(MESSAGE_FORBIDDEN, locale);
    }
    return getMessForLocale(MESSAGE_ERROR, locale);
  }
}
