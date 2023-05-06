package com.group.chitchat.model.dto;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

  @NotNull
  @NotEmpty
  private String userName;

  @NotNull
  private String role;

  @NotNull
  @NotEmpty
  private String email;
  private String avatar;

  @NotNull
  private Language nativeLanguage;

  @NotNull
  @NotEmpty
  private String firstname;

  @NotNull
  @NotEmpty
  private String lastname;

  @NotNull
  private Gender gender;

  @NotNull
  private LocalDate dob;


  @Override
  public String toString() {
    return "UserDto{" +
        "userName='" + userName + '\'' +
        ", role=" + role +
        ", email='" + email + '\'' +
        ", avatar='" + avatar + '\'' +
        ", nativeLanguage=" + nativeLanguage +
        '}';
  }
}
