package com.acme.learningcenter.security.domain.service.communication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  @Email
  private String email;

  @NotNull
  @NotBlank
  private String password;

  private Set<String> roles;

}
