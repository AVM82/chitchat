package com.group.chitchat.service;

import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.repository.LanguageRepo;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service that manages available languages.
 */
@Service
@AllArgsConstructor
public class LanguageService {

  private final LanguageRepo languageRepository;

  /**
   * Returns the set of currently available languages.
   *
   * @return Set available languages.
   */
  public Set<LanguageDto> getAvailableLanguages() {
    return languageRepository.findAll()
        .stream().map(LanguageDtoService::getFromEntity)
        .collect(Collectors.toSet());
  }
}
