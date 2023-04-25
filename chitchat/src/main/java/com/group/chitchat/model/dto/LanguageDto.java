package com.group.chitchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
        return "LanguageDto{" +
                "codeIso='" + codeIso + '\'' +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}
