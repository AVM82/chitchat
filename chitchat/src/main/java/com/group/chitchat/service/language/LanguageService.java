package com.group.chitchat.service.language;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.model.enums.Levels;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.service.internationalization.ResourcesBundleService;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service that manages available languages.
 */
@Service
@AllArgsConstructor
public class LanguageService {

  private final LanguageRepo languageRepository;
  private final ResourcesBundleService resourceBundleService;

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

  /**
   * Returns the array of currently available levels.
   *
   * @return array available language levels.
   */
  public Levels[] getAllLevels() {
    return Levels.values();
  }

  /**
   * Adds new language to database.
   *
   * @param languageDto Incoming dto with new language data  for adding to database.
   * @return Response with status and body with new language.
   */
  public ResponseEntity<LanguageDto> addLanguage(LanguageDto languageDto) {
    String codeIso = languageDto.getCodeIso();
    if (!isLanguageCorrect(codeIso)) {
      throw new IllegalArgumentException(
          String.format(resourceBundleService.getMessForLocale(
                          "message.code_ISO_not_correct", Locale.getDefault()),
              codeIso));
    } else {
      Language language = LanguageDtoService.getFromDto(languageDto);
      languageRepository.save(language);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(LanguageDtoService.getFromEntity(language));
    }
  }

  private boolean isLanguageCorrect(String codeIso) {

    return Arrays.asList(Locale.getISOLanguages()).contains(codeIso);
  }
}
