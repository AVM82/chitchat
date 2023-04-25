package com.group.chitchat.controller;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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
