package com.group.chitchat.data.auth;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String username;
  //Validation on email pattern.
  @Email(regexp = "^\\S+@\\S+\\.\\S+$",
      message = "Sorry, but you've just entered wrong email, pls try again.")
  private String email;
  private String password;
}