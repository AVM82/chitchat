package com.group.chitchat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "languages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
