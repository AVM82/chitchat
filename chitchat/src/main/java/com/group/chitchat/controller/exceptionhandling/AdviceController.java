package com.group.chitchat.controller.exceptionhandling;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.exception.LinkAdditionNotAllowedException;
import com.group.chitchat.exception.NotValidTranslationKeyException;
import com.group.chitchat.exception.RoleNotExistException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.exception.UserNotFoundException;
import com.group.chitchat.service.internationalization.BundlesService;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class AdviceController {

  private static final String LOG_INFO_FOR_EXCEPTIONS = "All info about exception {}";
  private static final String MESSAGE_ERROR = "e.general_error";

  private static String logInfoAndGiveMessage(String exceptionMessage) {
    log.info(LOG_INFO_FOR_EXCEPTIONS, exceptionMessage);
    return exceptionMessage;
  }

  private final BundlesService bundlesService;

  /**
   * Exception handler for runtime exception in case when user already exist.
   *
   * @param ex UserAlreadyExistException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(UserAlreadyExistException.class)
  public ErrorResponse userAlreadyExistException(UserAlreadyExistException ex) {
    return ErrorResponse.create(ex, CONFLICT, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exception in case when user not found.
   *
   * @param ex UserNotFoundException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler({UserNotFoundException.class})
  public ErrorResponse userNotFoundException(UserNotFoundException ex) {
    return ErrorResponse.create(ex, NOT_FOUND, logInfoAndGiveMessage(ex.getMessage()));
  }

  @ResponseBody
  @ExceptionHandler({ChitchatsNotFoundException.class})
  public ErrorResponse chitchatsNotFoundException(ChitchatsNotFoundException ex) {
    return ErrorResponse.create(ex, NOT_FOUND, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exception in case when role not exist.
   *
   * @param ex RoleNotExistException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(RoleNotExistException.class)
  public ErrorResponse roleDoesntExistException(RoleNotExistException ex) {
    return ErrorResponse.create(ex, NOT_FOUND, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exception in case when jwt token is expired.
   *
   * @param ex ExpiredJwtException.
   * @return the answer with meaning of this exception.
   */
  @ResponseBody
  @ExceptionHandler(ExpiredJwtException.class)
  public ErrorResponse expiredJwtException(ExpiredJwtException ex) {
    return ErrorResponse.create(ex, FORBIDDEN, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exception when not author trying to add link.
   *
   * @param ex LinkAdditionNotAllowedException.
   * @return a response with message of exception.
   */
  @ResponseBody
  @ExceptionHandler({LinkAdditionNotAllowedException.class})
  public ErrorResponse handleLinkAdditionNotAllowed(LinkAdditionNotAllowedException ex) {
    return ErrorResponse.create(ex, FORBIDDEN, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exception when key of message not found.
   *
   * @param ex NotValidTranslationKeyException.
   * @return a response with message of exception.
   */
  @ResponseBody
  @ExceptionHandler({NotValidTranslationKeyException.class})
  public ErrorResponse handleNotValidTranslationKey(NotValidTranslationKeyException ex) {
    return ErrorResponse.create(ex, NOT_FOUND, logInfoAndGiveMessage(ex.getMessage()));
  }

  /**
   * Exception handler for runtime exceptions when an authentication error occurs.
   *
   * @return a response with the value of this exception.
   */
  @ResponseBody
  @ExceptionHandler({AuthenticationException.class})
  public ErrorResponse errorAuthentication(AuthenticationException ex) {
    return ErrorResponse.create(ex, FORBIDDEN, logInfoAndGiveMessage(
        bundlesService.getExceptionMessForLocale(FORBIDDEN, Locale.getDefault())));
  }

  /**
   * Exception handler for runtime exceptions when any unhandled error occurs.
   *
   * @return generic error message.
   */
  @ResponseBody
  @ExceptionHandler({Exception.class})
  public ErrorResponse error(Exception ex) {
    ex.printStackTrace();
    return ErrorResponse.create(ex, INTERNAL_SERVER_ERROR, logInfoAndGiveMessage(
        bundlesService.getMessForLocale(MESSAGE_ERROR, Locale.getDefault())));
  }

  /**
   * Exception handler when data are not valid.
   *
   * @param ex MethodArgumentNotValidException
   * @return a response with the value of this exception.
   */
  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

    List<String> list = new ArrayList<>();
    for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
      String defaultMessage = objectError.getDefaultMessage();
      list.add(defaultMessage);
    }
    String errorMessage = list.toString();

    return ErrorResponse.create(ex, BAD_REQUEST, logInfoAndGiveMessage(errorMessage));
  }
}
