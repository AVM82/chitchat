package com.group.chitchat.controller;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RefreshRequest;
import com.group.chitchat.data.auth.RegisterRequest;
import com.group.chitchat.model.User;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.auth.AuthService;
import com.group.chitchat.service.auth.JwtEmailService;
import com.group.chitchat.service.auth.JwtService;
import com.group.chitchat.service.internationalization.BundlesService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationAndRegistrationController {

  private final AuthService authenticateService;
  private final JwtService jwtService;
  private final UserRepo userRepo;
  private final JwtEmailService jwtEmailService;
  private final BundlesService bundlesService;
  private final LocaleResolverConfig localeResolverConfig;

  /**
   * The method registers a new user in the application.
   *
   * @param request       data entered by the user for registration.
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return response about the status of user registration.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody @Valid RegisterRequest request
  ) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    log.info("User with username {} trying to register.", request.getUsername());
    return ResponseEntity.ok(authenticateService.register(request, requestHeader));
  }

  /**
   * The method performs user email authentication in the application.
   *
   * @param request       data entered by the user for authentication.
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return response about the status of user authentication.
   */
  @PostMapping("/click")
  public ResponseEntity<AuthenticationResponse> checkEmail(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody AuthenticationRequest request) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    String jwtEmailToken = request.getPassword();
    String jwtToken = null;
    if (!jwtEmailService.isEmailTokenExpired(jwtEmailToken)) {
      String username = jwtEmailService.extractUsername(jwtEmailToken);
      User newUser = userRepo.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException(
              String.format(bundlesService
                      .getMessForLocale("e.not_exist",
                          Locale.getDefault()),
                  username))
          );
      newUser.setEnabled(true);
      userRepo.save(newUser);
      jwtToken = jwtService.generateToken((UserDetails) newUser);
    }
    return ResponseEntity.ok(AuthenticationResponse.builder()
        .token(jwtToken)
        .build());
  }

  /**
   * The method performs user authentication in the application.
   *
   * @param request       data entered by the user for authentication.
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return response about the status of user authentication.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> login(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody AuthenticationRequest request) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    log.info("User with username {} trying to log in.", request.getUsername());
    return ResponseEntity.ok(authenticateService.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refresh (HttpServletRequest requestHeader,
      HttpServletResponse response,
      @RequestBody RefreshRequest request
  ) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return ResponseEntity.ok(authenticateService.refreshAllTokens(request));
  }
}
