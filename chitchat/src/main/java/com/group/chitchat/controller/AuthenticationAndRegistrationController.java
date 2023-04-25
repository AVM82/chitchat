package com.group.chitchat.controller;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RegisterRequest;

import javax.validation.Valid;

import com.group.chitchat.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody @Valid RegisterRequest request
  ) {
    log.info("User with username {} trying to register.", request.getUsername());
    return ResponseEntity.ok(authenticateService.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody AuthenticationRequest request
  ) {
    log.info("User with username {} trying to log in.", request.getUsername());
    return ResponseEntity.ok(authenticateService.authenticate(request));
  }
}
