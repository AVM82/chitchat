package com.group.chitchat.exception;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException(String username) {
    super("Sorry but username with username \"" + username +
        "\" already exist. Try another username.");
  }
}
