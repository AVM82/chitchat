package com.group.chitchat.model.dto.authdto;

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
public class AuthenticationRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;
  @NotBlank
  @Size(min = 5, max = 20)
  private String password;
  private String newPassword;
}