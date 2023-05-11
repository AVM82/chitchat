package com.group.chitchat.model.dto;

import com.group.chitchat.model.enums.Gender;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserForResponseDto {

  private String userName;
  private List<String> roles;
  private String email;
  private String avatar;
  private String nativeLanguage;
  private String firstname;
  private String lastname;
  private Gender gender;
  private LocalDate dob;

  @Override
  public String toString() {
    return "UserForResponseDto{"
        + "userName='" + userName + '\''
        + ", roles=" + roles
        + ", email='" + email + '\''
        + ", avatar='" + avatar + '\''
        + ", nativeLanguage='" + nativeLanguage + '\''
        + ", firstname='" + firstname + '\''
        + ", lastname='" + lastname + '\''
        + ", gender=" + gender
        + ", dob=" + dob
        + '}';
  }
}
