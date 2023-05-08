package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserForEditDto {

  private String firstname;
  private String lastname;
  private String role;
  private String avatar;
  private String nativeLanguage;
  private LocalDate dob;
  private Gender gender;

  @Override
  public String toString() {
    return "UserForEditDto{"
        + "firstname='" + firstname + '\''
        + ", lastname='" + lastname + '\''
        + ", role='" + role + '\''
        + ", avatar='" + avatar + '\''
        + ", nativeLanguage='" + nativeLanguage + '\''
        + ", dob=" + dob
        + ", gender=" + gender
        + '}';
  }
}
