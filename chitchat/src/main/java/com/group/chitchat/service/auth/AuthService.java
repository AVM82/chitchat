package com.group.chitchat.service.auth;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RegisterRequest;
import com.group.chitchat.exception.RoleDoesntExistException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.model.Role;
import com.group.chitchat.model.User;
import com.group.chitchat.repository.RoleRepo;
import com.group.chitchat.repository.UserRepo;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class AuthService {

  private final UserRepo userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService service;
  private final AuthenticationManager authenticationManager;
  private final RoleRepo roleRepository;
  private static final String USER_ROLE = "ROLE_USER";

  public AuthenticationResponse register(@Valid RegisterRequest request) {
    String username = request.getUsername();
    if (userRepository.existsByUsername(username)) {
      throw new UserAlreadyExistException(username);
    }

    User user = buildNewUser(username, request.getEmail(), request.getPassword());
    user.setRoles(setUserDefaultRole());
    log.info(user.getRoles());
    userRepository.save(user);

    var jwtToken = service.generateToken(user);
    log.info("User register with username {} successfully.", username);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    String username = request.getUsername();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
                "User with username " + username + " not found"
            )
        );


    log.info(user.getRoles());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            username,
            request.getPassword()
        )
    );

    var jwtToken = service.generateToken(user);
    log.info("User have log in successfully.");
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private User buildNewUser(String username, String email, String password) {
    return User.builder()
        .username(username)
        .email(email)
        .password(passwordEncoder.encode(password))
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .build();
  }

  private Set<Role> setUserDefaultRole() {
    Set<Role> userRoles = new HashSet<>();
    userRoles.add(roleRepository.findRoleByName(USER_ROLE)
        .orElseThrow(() -> new RoleDoesntExistException(USER_ROLE)));
    return userRoles;
  }
}
