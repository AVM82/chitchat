package com.group.chitchat.exception;

public class RoleDoesntExistException extends RuntimeException {

  public RoleDoesntExistException(String userRole) {
    super("Sorry but \"" + userRole + "\" doesn't exist in db!");
  }
}
