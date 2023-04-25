package com.group.chitchat.service;

import com.group.chitchat.model.Language;
import com.group.chitchat.repository.LanguageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepo languageRepository;

    public List<Language> getAvailableLanguages() {
        return languageRepository.findAll();
    }
}
