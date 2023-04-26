package com.group.chitchat.controller;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.service.language.LanguageService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/get_languages")
public class LanguageController {

  private final LanguageService languageService;

  @GetMapping
  public ResponseEntity<Set<LanguageDto>> getAllLanguages() {
    return ResponseEntity.ok(languageService.getAvailableLanguages());
  }
}
