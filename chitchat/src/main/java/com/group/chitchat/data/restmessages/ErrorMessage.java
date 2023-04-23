package com.group.chitchat.data.restmessages;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
  private String message;
  private HttpStatus code;
  private LocalDateTime timestamp;
}
