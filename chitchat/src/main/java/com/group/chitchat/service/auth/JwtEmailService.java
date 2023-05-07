package com.group.chitchat.service.auth;

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
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtEmailService {

  private final Environment environment;
  /**
   * This constant is responsible for how long will code run.
   */
  private static final int HOW_LONG_WILL_CODE_WORK = 1000 * 60 * 630;

  public String extractUsername(String jwtToken) {
    return extractClaim(jwtToken, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateEmailToken(UserDetails userDetails) {
    return generateEmailToken(new HashMap<>(), userDetails);
  }

  /**
   * Generate and return token.
   *
   * @param extractClaims extract claims.
   * @param userDetails   user details.
   * @return jwt token.
   */
  public String generateEmailToken(
      Map<String, Object> extractClaims,
      UserDetails userDetails
  ) {

    return Jwts
        .builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + HOW_LONG_WILL_CODE_WORK))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isEmailTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
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

