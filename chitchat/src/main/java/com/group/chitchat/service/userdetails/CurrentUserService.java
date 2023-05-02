package com.group.chitchat.service.userdetails;

import com.group.chitchat.service.internationalization.ResourceBundleService;
import com.group.chitchat.service.internationalization.ResourcesBundleService;
import java.util.Locale;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

  private CurrentUserService() {
    //Some empty constructor
  }

  private static final ResourcesBundleService resourceBundleService =
      new ResourceBundleService();

  /**
   * Method which helps to get username of user who send request.
   *
   * @return username
   */
  public static String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return authentication.getName();
    }
    throw new UsernameNotFoundException(resourceBundleService.getMessForLocale(
            "exception.username", Locale.getDefault()));
  }

}