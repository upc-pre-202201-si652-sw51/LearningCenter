package com.acme.learningcenter.security.domain.service.communication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticateRequest {

  @NotNull
  @NotBlank
  private String username;
  @NotNull
  @NotBlank
  private String password;
}
