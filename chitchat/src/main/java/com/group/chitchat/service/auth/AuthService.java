package com.group.chitchat.service.auth;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RegisterRequest;
import com.group.chitchat.exception.RoleNotExistException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.model.Role;
import com.group.chitchat.model.User;
import com.group.chitchat.repository.RoleRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.internationalization.BundlesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service which manage registration and authentication.
 */
@Service
@AllArgsConstructor
@Log4j2
public class AuthService {

  private final EmailService emailService;
  private final UserRepo userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService service;
  private final JwtEmailService jwtEmailService;
  private final AuthenticationManager authenticationManager;
  private final RoleRepo roleRepository;
  private static final String USER_ROLE = "ROLE_USER";
  private final BundlesService bundlesService;

  /**
   * Register method which take data from request and create new user. After all this steps saving
   * this user to db.
   *
   * @param request request with info about user.
   * @return JWT token.
   */
  public AuthenticationResponse register(@Valid RegisterRequest request,
      HttpServletRequest httpRequest) {

    String username = request.getUsername();
    if (userRepository.existsByUsername(username)) {
      throw new UserAlreadyExistException(username);
    }

    User user = buildNewUser(username, request.getEmail(), request.getPassword());
    //hot fix need to replace with not costul
    Role defaultRole = getDefaultRoleOrThrowException();
    defaultRole.setUsers(new HashSet<>());
    defaultRole.getUsers().add(user);
    user.getRoles().add(defaultRole);

    log.info(user.getRoles());
    roleRepository.save(defaultRole);
    userRepository.save(user);

    var jwtEmailToken = jwtEmailService.generateEmailToken(user);
    // Log info about user who had registered in db.
    log.info("User register with username {} successfully.", username);
    String url = httpRequest.getRequestURL().toString().replace("register", "?click=");
    sendEmail(user, url + jwtEmailToken);
    return AuthenticationResponse.builder()
        .token(jwtEmailToken)
        .build();
  }

  /**
   * Authentication method which giving jwt token for user what have registered before.
   *
   * @param request request with username and password.
   * @return JWT token.
   */
  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    String username = request.getUsername();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(

            String.format(bundlesService
                    .getMessForLocale("e.not_exist",
                        Locale.getDefault()),
                username))
        );

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
        .roles(new HashSet<>())
        .enabled(false)
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .build();
  }

  private void sendEmail(User user, String message) {
    emailService.sendEmail(
        user.getEmail(),
        String.format("New ChitChat user registered: %s", user.getUsername()),
        message);
  }

  private Role getDefaultRoleOrThrowException() {
    return roleRepository.findRoleByName(USER_ROLE)
        .orElseThrow(() -> new RoleNotExistException(USER_ROLE));
  }
}
