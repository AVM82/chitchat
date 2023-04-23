package com.group.chitchat.exception;

public class RoleNotExistException extends RuntimeException {

  public RoleNotExistException(String userRole) {
    super("Sorry but \"" + userRole + "\" doesn't exist in db!");
  }
}
