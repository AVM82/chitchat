package com.group.chitchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "languages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Language {

  @Id
  @Column(name = "id", unique = true)
  private String codeIso;
  @Column(unique = true)
  private String name;        //TODO change value to key from i18n file

  @Override
  public String toString() {
    return "Language{"
        + "codeIso='" + codeIso + '\''
        + ", language='" + name + '\''
        + '}';
  }
}
