package com.group.chitchat.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class LanguageApplicationConfig implements WebMvcConfigurer {

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
      "classpath:/static/"
  };

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

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!registry.hasMappingForPattern("/**")) {
      registry.addResourceHandler("/**")
          .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String crossOrigins = "*";
    registry.addMapping("/api/**")
        .allowedOrigins(crossOrigins)
        .maxAge(3600);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    String viewName = "index.html";
    registry.addViewController("/").setViewName(viewName);
    registry.addViewController("/chitchat").setViewName(viewName);
    registry.addViewController("/profile").setViewName(viewName);
    registry.addViewController("/click").setViewName(viewName);
  }

  /**
   * An internalResourceViewResolver.
   * @return internalResourceViewResolver bean
   */
  @Bean
  public ViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(InternalResourceView.class);
    return viewResolver;
  }
}
