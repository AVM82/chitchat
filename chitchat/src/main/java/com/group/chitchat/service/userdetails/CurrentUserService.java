package com.group.chitchat.service.userdetails;

import com.group.chitchat.service.internationalization.BundleService;
import com.group.chitchat.service.internationalization.BundlesService;
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

  private static final BundlesService resourceBundleService =
      new BundleService();

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
        "e.user_not_found", Locale.getDefault()));
  }
}