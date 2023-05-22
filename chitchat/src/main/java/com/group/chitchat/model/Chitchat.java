package com.group.chitchat.model;

import com.group.chitchat.model.enums.Levels;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@Table(name = "chitchats")
@NoArgsConstructor
@AllArgsConstructor
public class Chitchat {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "header")
  private String chatName;
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private User author;
  @ManyToOne(targetEntity = Category.class)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;
  @Column(name = "description")
  private String description;
  @ManyToOne(targetEntity = Language.class)
  @JoinColumn(name = "language_id", referencedColumnName = "id")
  private Language language;
  @Enumerated(EnumType.STRING)
  @Column(name = "level")
  private Levels level;
  @Column(name = "capacity")
  private int capacity;
  @Column(name = "time")
  private LocalDateTime date;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "chitchat_users",
      joinColumns = @JoinColumn(name = "chitchat_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> usersInChitchat = new LinkedHashSet<>();
  @Column(name = "link")
  private String conferenceLink;
  @OneToOne(mappedBy = "chitchat", cascade = CascadeType.ALL)
  private RemindersData remindersData;

  @Override
  public String toString() {
    return "Chitchat{"
        + "id="
        + id
        + ", chatName='"
        + chatName
        + '\''
        + ", authorId="
        + author
        + ", categoryId="
        + category
        + ", description='"
        + description
        + '\''
        + ", languageId="
        + language
        + ", level="
        + level
        + ", capacity="
        + capacity
        + ", date="
        + date
        + ", usersInChitchat="
        + usersInChitchat
        + '}';
  }
}
