package com.group.chitchat.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LanguageApplicationConfig implements WebMvcConfigurer {

  /**
   * Determines which locale is currently in use.
   *
   * @return An object that defines the current locale.
   */
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  /**
   * An application context delegates the message resolution to a bean with the exact name
   * messageSource.
   *
   * @return An object of the messageSource class with the assigned parameters.
   */
  @Bean("messageSource")
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource =
        new ResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
