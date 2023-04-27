package com.group.chitchat.service.language;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.dto.LanguageDto;

public class LanguageDtoService {

  private LanguageDtoService() {
  }

  public static LanguageDto getFromEntity(Language language) {
    return new LanguageDto(language.getCodeIso(), language.getName());
  }

  /**
   * Map entity from dto.
   *
   * @param languageDto language dto
   * @return entity of language
   */
  public static Language getFromDto(LanguageDto languageDto) {
    return Language.builder()
        .codeIso(languageDto.getCodeIso())
        .name(languageDto.getLanguageName())
        .build();
  }
}
