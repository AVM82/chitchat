package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.ResourceBundleService;
import java.util.Locale;

public class RoleNotExistException extends RuntimeException {

  /**
   * Exceptions are cases where the user role is not found in the database.
   *
   * @param userRole The user role.
   */
  public RoleNotExistException(String userRole) {

    super(new ResourceBundleService().getMessForLocale(
            "exception.sorry_but", Locale.getDefault())
        + "\"" + userRole + "\""
        + new ResourceBundleService().getMessForLocale(
            "exception.not_exist", Locale.getDefault()));
  }
}
