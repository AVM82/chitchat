package com.group.chitchat.controller;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RegisterRequest;
import com.group.chitchat.service.ResourcesBundleService;
import com.group.chitchat.service.auth.AuthService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationAndRegistrationController {

  private final AuthService authenticateService;
  private final ResourcesBundleService resourceBundleService;

  /**
   * The method registers a new user in the application.
   *
   * @param request data entered by the user for registration.
   * @param locale  locale information is specified in the Accept-Language parameter.
   * @return response about the status of user registration.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody @Valid RegisterRequest request,
      @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale locale
  ) {
    resourceBundleService.setLocale(locale);
    log.info("User with username {} trying to register.", request.getUsername());
    return ResponseEntity.ok(authenticateService.register(request));
  }

  /**
   * The method performs user authentication in the application.
   *
   * @param request data entered by the user for authentication.
   * @param locale  locale information is specified in the Accept-Language parameter.
   * @return response about the status of user authentication.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody AuthenticationRequest request,
      @RequestHeader(value = "Accept-Language", name = "Accept-Language",
          required = false, defaultValue = "en") Locale locale
  ) {
    resourceBundleService.setLocale(locale);
    log.info("User with username {} trying to log in.", request.getUsername());
    return ResponseEntity.ok(authenticateService.authenticate(request));
  }
}
