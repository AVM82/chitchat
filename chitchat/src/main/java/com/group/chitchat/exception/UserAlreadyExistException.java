package com.group.chitchat.exception;

public class UserAlreadyExistException extends RuntimeException {

  /**
   * Exception for cases when user trying to register with same info
   * as user what have registered before.
   * @param username Name of the user.
   */
  public UserAlreadyExistException(String username) {
    super("Sorry but username with username \""
        + username
        + "\" already exist. Try another username.");
  }
}
