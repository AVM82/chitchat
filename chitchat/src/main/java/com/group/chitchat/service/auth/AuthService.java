package com.group.chitchat.service.auth;

import com.group.chitchat.data.auth.AuthenticationRequest;
import com.group.chitchat.data.auth.AuthenticationResponse;
import com.group.chitchat.data.auth.RefreshRequest;
import com.group.chitchat.data.auth.RegisterRequest;
import com.group.chitchat.exception.RoleNotExistException;
import com.group.chitchat.exception.TokenNotFoundException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.model.RefreshToken;
import com.group.chitchat.model.Role;
import com.group.chitchat.model.User;
import com.group.chitchat.repository.RefreshTokenRepo;
import com.group.chitchat.repository.RoleRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.internationalization.BundlesService;
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

  private final UserRepo userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService service;
  private final AuthenticationManager authenticationManager;
  private final RoleRepo roleRepository;
  private final RefreshTokenRepo tokenRepo;
  private static final String USER_ROLE = "ROLE_USER";
  private final BundlesService bundlesService;

  /**
   * Register method which take data from request and create new user. After all this steps saving
   * this user to db.
   *
   * @param request request with info about user.
   * @return JWT token.
   */
  public AuthenticationResponse register(@Valid RegisterRequest request) {

    String username = request.getUsername();
    if (userRepository.existsByUsername(username)) {
      throw new UserAlreadyExistException(username);
    }

    User user = buildNewUser(username, request.getEmail(), request.getPassword());
    Role defaultRole = getDefaultRoleOrThrowException();
    defaultRole.setUsers(new HashSet<>());
    defaultRole.getUsers().add(user);
    user.getRoles().add(defaultRole);

    roleRepository.save(defaultRole);
    userRepository.save(user);

    // Log info about user who had registered in db.
    log.info("User register with username {} successfully.", username);
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

    return updateExistsTokens(user,
        tokenRepo.findRefreshTokenByOwnerOfToken(user)
            .orElseThrow(TokenNotFoundException::new)
    );
  }

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
        .roles(new HashSet<>())
        .enabled(true)
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

  private Role getDefaultRoleOrThrowException() {
    return roleRepository.findRoleByName(USER_ROLE)
        .orElseThrow(() -> new RoleNotExistException(USER_ROLE));
  }
}
