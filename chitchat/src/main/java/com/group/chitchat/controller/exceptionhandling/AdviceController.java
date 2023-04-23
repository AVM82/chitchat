package com.group.chitchat.controller.exceptionhandling;

import com.group.chitchat.data.restmessages.ErrorMessage;
import com.group.chitchat.exception.RoleNotExistException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Log4j2
public class AdviceController {

  private static final String LOG_INFO_FOR_EXCEPTIONS = "All info about exception {}";
  private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
  private static final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
  private static final HttpStatus CONFLICT = HttpStatus.CONFLICT;

  private static String logInfoAndGiveMessage(String exceptionMessage) {
    log.info(LOG_INFO_FOR_EXCEPTIONS, exceptionMessage);
    return exceptionMessage;
  }

  /**
   * Exception handler for runtime exception in case when user already exist.
   * @param exception UserAlreadyExistException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorMessage> userAlreadyExistException(
      UserAlreadyExistException exception) {
    return new ResponseEntity<>(ErrorMessage.builder()
        .code(CONFLICT)
        .timestamp(LocalDateTime.now())
        .message(logInfoAndGiveMessage(exception.getMessage()))
        .build(), CONFLICT);
  }

  /**
   * Exception handler for runtime exception in case when user not found.
   * @param exception UserNotFoundException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorMessage> userNotFoundException(
      UserNotFoundException exception) {
    return new ResponseEntity<>(ErrorMessage.builder()
        .code(NOT_FOUND)
        .timestamp(LocalDateTime.now())
        .message(logInfoAndGiveMessage(exception.getMessage()))
        .build(), NOT_FOUND);
  }

  /**
   * Exception handler for runtime exception in case when role not exist.
   * @param exception RoleNotExistException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(RoleNotExistException.class)
  public ResponseEntity<ErrorMessage> roleDoesntExistException(
      RoleNotExistException exception) {
    return new ResponseEntity<>(ErrorMessage.builder()
        .code(NOT_FOUND)
        .timestamp(LocalDateTime.now())
        .message(logInfoAndGiveMessage(exception.getMessage()))
        .build(), NOT_FOUND);
  }

  /**
   * Exception handler for runtime exception in case when jwt token is expired.
   * @param exception ExpiredJwtException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ErrorMessage> expiredJwtException(
      ExpiredJwtException exception) {
    return new ResponseEntity<>(ErrorMessage.builder()
        .code(FORBIDDEN)
        .timestamp(LocalDateTime.now())
        .message(logInfoAndGiveMessage(exception.getMessage()))
        .build(), FORBIDDEN);
  }
}