package com.group.chitchat.service.internationalization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class LocaleResolverConfig implements LocaleResolver {

  private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("uk", "en", "de");

  @Override
  public Locale resolveLocale(HttpServletRequest request) {

    String language = request.getHeader("Accept-Language");
    if (language == null || language.isEmpty()) {
      return Locale.forLanguageTag("en");
    }
    if (SUPPORTED_LANGUAGES.contains(language)) {
      return Locale.forLanguageTag(language);
    }
    return Locale.forLanguageTag("en");
  }

  @Override
  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    String language = request.getHeader("Accept-Language");
    if (language == null || language.isEmpty()) {
      Locale.setDefault(Locale.US);
    } else if (SUPPORTED_LANGUAGES.contains(language)) {
      Locale.setDefault(Locale.forLanguageTag(language));
    } else {
      Locale.setDefault(Locale.US);
    }
  }
}
