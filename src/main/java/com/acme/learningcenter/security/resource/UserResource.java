package com.acme.learningcenter.security.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class UserResource {
  private Long id;
  private String username;
  private String email;
  private List<RoleResource> roles;
}
