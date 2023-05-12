package com.group.chitchat.service.auth;

import com.group.chitchat.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class JwtService {

  private final Environment environment;

  /**
   * This constant is responsible for how long will code run.
   */
  private static final long HOW_LONG_WILL_REFRESH_WORK = 1000L * 60L * 60L * 10;

  /**
   * This constant is responsible for how long will code run.
   */
  private static final int HOW_LONG_WILL_TOKEN_WORK = 1000 * 60 * 60 * 8;

  public String extractUsername(String jwtToken) {
    return extractClaim(jwtToken, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extract token id.
   *
   * @param jwtToken token for extract id
   * @return id
   */
  public String extractTokenId(String jwtToken) {
    final Claims claims = extractAllClaims(jwtToken);
    return claims.getId();
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generate and return token.
   *
   * @param extractClaims extract claims.
   * @param userDetails   user details.
   * @return jwt token.
   */
  public String generateToken(Map<String, Object> extractClaims,
      UserDetails userDetails
  ) {
    User userMy = (User) userDetails;
    extractClaims.put("user_id", userMy.getId());
    extractClaims.put("user_name", userMy.getUsername());
    return Jwts
        .builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + HOW_LONG_WILL_TOKEN_WORK))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(UserDetails userDetails, Long tokenId) {
    return generateRefreshToken(new HashMap<>(), userDetails, tokenId);
  }

  /**
   * Generator of refresh tokens.
   * @param extractClaims extract claims for jwts
   * @param userDetails userDetails for set id and username
   * @param tokenId token id to set id
   * @return token
   */
  public String generateRefreshToken(Map<String, Object> extractClaims,
      UserDetails userDetails,
      Long tokenId
  ) {
    User userMy = (User) userDetails;
    extractClaims.put("user_id", userMy.getId());
    return Jwts
        .builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setId(String.valueOf(tokenId))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + HOW_LONG_WILL_REFRESH_WORK))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Make sure that token valid.
   * @param token refresh token to check it
   * @param userDetails user details to make sure that user is owner
   * @param tokenId token id
   * @return boolean value
   */
  public boolean isTokenValid(String token, UserDetails userDetails, long tokenId) {
    final String username = extractUsername(token);
    final long tokenIdNumber = Long.parseLong(extractTokenId(token));
    return (username.equals(userDetails.getUsername()))
        && (tokenIdNumber == tokenId) && isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return !extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBites = Decoders.BASE64.decode(environment.getProperty("secret.key"));
    return Keys.hmacShaKeyFor(keyBites);
  }
}
