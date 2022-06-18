package com.acme.learningcenter.security.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;



@Component
public class JwtHandler {
  private static final Logger logger = LoggerFactory.getLogger(JwtHandler.class);

  @Value("${authorization.jwt.secret}")
  private String secret;

  @Value("${authorization.jwt.expiration.days}")
  private int expirationDays;

  public String generateToken(Authentication authentication) {
    //String subject = ((UserDetailsI))
    return null;
  }
}
