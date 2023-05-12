package com.group.chitchat.service.auth;

import com.group.chitchat.exception.TokenNotFoundException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.model.RefreshToken;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.authdto.AuthenticationRequest;
import com.group.chitchat.model.dto.authdto.AuthenticationResponse;
import com.group.chitchat.model.dto.authdto.RefreshRequest;
import com.group.chitchat.model.dto.authdto.RegisterRequest;
import com.group.chitchat.repository.RefreshTokenRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.internationalization.BundlesService;
import com.group.chitchat.service.profile.RoleService;
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
  private final RefreshTokenRepo tokenRepo;
  private final BundlesService bundlesService;
  private final RoleService roleService;

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
      log.info("User {} already exist", username);
      throw new UserAlreadyExistException(username);
    }
    User user = buildNewUser(username, request.getEmail(), request.getPassword());
    roleService.setDefaultRole(user);
    roleService.setDefaultPermission(user);
    userRepository.save(user);
    log.info(user.getRoles());

    var jwtEmailToken = jwtEmailService.generateEmailToken(user);
    // Log info about user who had registered in db.
    log.info("User register with username {} successfully.", username);
    String url = httpRequest.getRequestURL().toString()
        .replace("/api/v1/auth/register", "/click?click=");
    sendEmail(user, url + jwtEmailToken);

    return buildNewTokens(user);
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

    log.info("User have log in successfully.");
    if (tokenRepo.findRefreshTokenByOwnerOfToken(user).isEmpty()) {
      buildNewTokens(user);
    }
    return updateExistsTokens(user,
        tokenRepo.findRefreshTokenByOwnerOfToken(user)
            .orElseThrow(TokenNotFoundException::new)
    );
  }

  /**
   * Refresh all tokens.
   *
   * @param request contains refresh token
   * @return two new tokens
   */
  public AuthenticationResponse refreshAllTokens(RefreshRequest request) {
    RefreshToken oldRefreshToken = tokenRepo
        .findRefreshTokenByTokenForRefresh(request.getRefreshToken())
        .orElseThrow(TokenNotFoundException::new);

    return updateExistsTokens(oldRefreshToken.getOwnerOfToken(), oldRefreshToken);
  }

  private User buildNewUser(String username, String email, String password) {
    return User.builder()
        .username(username)
        .email(email)
        .password(passwordEncoder.encode(password))
        .enabled(false)
        .roles(new HashSet<>())
        .permissions(new HashSet<>())
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .build();
  }

  private AuthenticationResponse buildNewTokens(User user) {
    RefreshToken refreshTokenForDb = new RefreshToken();
    refreshTokenForDb.setOwnerOfToken(user);

    var jwtToken = service.generateToken(user);

    var refreshToken = service.generateRefreshToken(user, refreshTokenForDb.getId());

    refreshTokenForDb.setTokenForRefresh(refreshToken);

    tokenRepo.save(refreshTokenForDb);

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private AuthenticationResponse updateExistsTokens(User user, RefreshToken oldToken) {
    var jwtToken = service.generateToken(user);

    var refreshToken = service.generateRefreshToken(user, oldToken.getId());
    oldToken.setTokenForRefresh(refreshToken);

    tokenRepo.save(oldToken);

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void sendEmail(User user, String message) {
    emailService.sendEmail(
        user.getEmail(),
        String.format("New ChitChat user registered: %s", user.getUsername()),
        message);
  }

  /**
   * Send password recovery link by e-mail.
   *
   * @param user        User for password recovery
   * @param httpRequest Http request
   */
  public void passwordRecoveryEmail(User user, HttpServletRequest httpRequest) {
    var jwtEmailToken = jwtEmailService.generateEmailToken(user);
    String url = httpRequest.getRequestURL().toString()
        .replace("/api/v1/auth/password_recovery_email", "/password_recovery?click=");
    emailService.sendEmail(
        user.getEmail(),
        String.format("Link for password recovery: %s", user.getUsername()),
        url + jwtEmailToken);
  }

  /**
   * Set new password recovery by e-mail.
   *
   * @param user        User
   * @param newPassword New password
   */
  public void passwordRecoveryConfirm(User user, String newPassword) {
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }
}
