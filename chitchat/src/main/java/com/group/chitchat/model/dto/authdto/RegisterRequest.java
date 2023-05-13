package com.group.chitchat.model.dto.authdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;
  //Validation on email pattern.
  @Email(regexp = "^\\S+@\\S+\\.\\S+$",
      message = "Sorry, but you've just entered wrong email, pls try again.")
  private String email;
  @NotBlank
  @Size(min = 5)
  private String password;
}