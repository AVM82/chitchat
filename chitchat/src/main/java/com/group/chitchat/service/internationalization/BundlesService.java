package com.group.chitchat.service.internationalization;

import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public interface BundlesService {


  String getMessForLocale(String keyMessage, Locale locale);

  String getExceptionMessForLocale(HttpStatus httpStatus, Locale locale);
}
