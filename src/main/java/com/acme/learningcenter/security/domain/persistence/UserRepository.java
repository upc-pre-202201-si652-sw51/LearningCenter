package com.acme.learningcenter.security.domain.persistence;

import com.acme.learningcenter.security.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}
