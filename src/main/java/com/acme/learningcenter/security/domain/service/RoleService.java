package com.acme.learningcenter.security.domain.service;

import com.acme.learningcenter.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {
  void seed();
  List<Role> getAll();
}
