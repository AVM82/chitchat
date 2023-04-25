package com.group.chitchat.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageDto {

  @NotNull
  String codeIso;
  @NotNull
  String languageName;

  @Override
  public String toString() {
    return "LanguageDto{"
        + "codeIso='" + codeIso + '\''
        + ", languageName='" + languageName + '\''
        + '}';
  }
}
