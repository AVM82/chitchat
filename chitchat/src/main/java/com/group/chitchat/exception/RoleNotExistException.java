package com.group.chitchat.exception;

import com.group.chitchat.service.internationalization.BundleService;
import java.util.Locale;

public class RoleNotExistException extends RuntimeException {

  /**
   * Exceptions are cases where the user role is not found in the database.
   *
   * @param userRole The user role.
   */
  public RoleNotExistException(String userRole) {

    super(String.format(
        new BundleService()
            .getMessForLocale("e.not_exist",
                Locale.getDefault()),
        userRole));
  }
}
