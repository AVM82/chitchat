package com.group.chitchat.service;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public interface ResourcesBundleService {

  ResourceBundle getResourceBundle(Locale locale);

  String getMessForLocale(String keyMessage, Locale locale);

  String getExceptionMessForLocale(HttpStatus httpStatus, Locale locale);

  void setLocale(Locale locale);
}
