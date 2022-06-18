package com.acme.learningcenter.security.middleware;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtHandler {
  private static final Logger logger = LoggerFactory.getLogger(JwtHandler.class);

  @Value("${authorization.jwt.secret}")
  private String secret;

  @Value("${authorization.jwt.expiration.days}")
  private int expirationDays;

  public String generateToken(Authentication authentication) {
    String subject = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    Date issuedAt = new Date();
    Date expiration = DateUtils.addDays(issuedAt, expirationDays);
    return Jwts.builder()
      .setSubject(subject)
      .setIssuedAt(issuedAt)
      .setExpiration(expiration)
      .signWith(SignatureAlgorithm.ES256, secret)
      .compact();
  }

  public String getUsernameFrom(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret)
        .parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JSON Web Token Signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JSON Web Token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JSON Web Token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JSON Web Token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JSON Web Token claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
