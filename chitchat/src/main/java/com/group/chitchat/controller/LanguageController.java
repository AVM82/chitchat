package com.group.chitchat.controller;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.model.enums.Levels;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.language.LanguageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/languages")
public class LanguageController {

  private final LanguageService languageService;
  private final LocaleResolverConfig localeResolverConfig;

  @GetMapping("/all")
  public ResponseEntity<Set<LanguageDto>> getAllLanguages(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return ResponseEntity.ok(languageService.getAvailableLanguages());
  }

  @GetMapping("/levels")
  public ResponseEntity<Levels[]> getAllLevels(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return ResponseEntity.ok(languageService.getAllLevels());
  }


  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<LanguageDto> addLanguage(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody LanguageDto languageDto) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return languageService.addLanguage(languageDto);
  }
}
