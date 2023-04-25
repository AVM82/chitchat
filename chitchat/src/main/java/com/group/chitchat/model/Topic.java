package com.group.chitchat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotNull
  @Column(unique = true)
  private String name;                          //TODO change to key from i18n table
  @Column(columnDefinition = "int4 DEFAULT 0")
  private int priority;                         //TODO add automatic priority update

  public Topic(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Topic{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", priority=" + priority
        + '}';
  }
}
