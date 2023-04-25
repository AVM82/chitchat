package com.group.chitchat.service;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.repository.LanguageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepo languageRepository;

    public Set<LanguageDto> getAvailableLanguages() {
        return languageRepository.findAll()
                .stream().map(LanguageDtoService::getFromEntity)
                .collect(Collectors.toSet());
    }
}
