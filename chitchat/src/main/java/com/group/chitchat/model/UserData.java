package com.group.chitchat.model;

import com.group.chitchat.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserData {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "lastname")
  private String lastName;

  @Column(name = "avatar")
  private String avatar;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "dob")
  private LocalDate dob;

  @ManyToOne(targetEntity = Language.class)
  @JoinColumn(name = "native_language", referencedColumnName = "id")
  private Language nativeLanguage;

  @OneToOne
  @MapsId
  private User user;

  @Override
  public String toString() {
    return "UserData{"
        + "userId=" + userId
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", avatar='" + avatar + '\''
        + ", gender=" + gender
        + ", dob=" + dob
        + ", nativeLanguage=" + nativeLanguage
        + ", user=" + user
        + '}';
  }
}
