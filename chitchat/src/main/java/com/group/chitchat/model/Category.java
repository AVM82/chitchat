package com.group.chitchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotNull
  @Column(unique = true)
  private String name;                          //TODO change to key from i18n table
  @Column(columnDefinition = "int4 DEFAULT 0")
  private int priority;                         //TODO add automatic priority update

  public Category(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Category{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", priority=" + priority
        + '}';
  }
}
