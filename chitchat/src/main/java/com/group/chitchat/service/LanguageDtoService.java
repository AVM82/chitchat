package com.group.chitchat.service;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.dto.LanguageDto;

public class LanguageDtoService {

  private LanguageDtoService() {
  }

  public static LanguageDto getFromEntity(Language language) {
    return new LanguageDto(language.getCodeIso(), language.getName());
  }

  public static Language getFromDto(LanguageDto languageDto) {
    return new Language(languageDto.getCodeIso(), languageDto.getLanguageName());
  }
}
