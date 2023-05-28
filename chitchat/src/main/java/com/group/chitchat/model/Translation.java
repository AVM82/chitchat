package com.group.chitchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Translation {

  @Id
  @Column(name = "id")
  private int id;
  @Column(name = "message_key")
  private String messageKey;
  @Column(name = "locale")
  private Locale locale;
  @Column(name = "message")
  private String message;
}
