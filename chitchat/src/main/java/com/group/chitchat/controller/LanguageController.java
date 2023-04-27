package com.group.chitchat.controller;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.service.LanguageService;
import com.group.chitchat.service.ResourcesBundleService;
import java.util.Locale;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/get_languages")
public class LanguageController {

  private final LanguageService languageService;
  private final ResourcesBundleService resourceBundleService;

  @GetMapping
  public ResponseEntity<Set<LanguageDto>> getAllLanguages(
      @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale locale
  ) {
    resourceBundleService.setLocale(locale);
    return ResponseEntity.ok(languageService.getAvailableLanguages());
  }
}
