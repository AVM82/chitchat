package com.group.chitchat.service.language;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.dto.LanguageDto;
import com.group.chitchat.model.enums.Levels;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.service.internationalization.BundlesService;
import java.util.Arrays;
import java.util.Locale;
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
  private final BundlesService bundlesService;

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
   * @return dto with new language.
   */
  public LanguageDto addLanguage(LanguageDto languageDto) {
    String codeIso = languageDto.getCodeIso();
    if (!isLanguageCorrect(codeIso)) {
      throw new IllegalArgumentException(
          String.format(bundlesService.getMessForLocale(
                  "m.code_ISO_not_correct", Locale.getDefault()),
              codeIso));
    } else {
      Language language = LanguageDtoService.getFromDto(languageDto);
      languageRepository.save(language);

      return LanguageDtoService.getFromEntity(language);
    }
  }

  private boolean isLanguageCorrect(String codeIso) {

    return Arrays.asList(Locale.getISOLanguages()).contains(codeIso);
  }
}
