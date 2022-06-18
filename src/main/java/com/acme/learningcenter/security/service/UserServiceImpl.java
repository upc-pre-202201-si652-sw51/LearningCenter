package com.acme.learningcenter.security.service;

import com.acme.learningcenter.security.domain.model.entity.Role;
import com.acme.learningcenter.security.domain.model.entity.User;
import com.acme.learningcenter.security.domain.model.enumeration.Roles;
import com.acme.learningcenter.security.domain.persistence.RoleRepository;
import com.acme.learningcenter.security.domain.persistence.UserRepository;
import com.acme.learningcenter.security.domain.service.UserService;
import com.acme.learningcenter.security.domain.service.communication.AuthenticateRequest;
import com.acme.learningcenter.security.domain.service.communication.AuthenticateResponse;
import com.acme.learningcenter.security.domain.service.communication.RegisterRequest;
import com.acme.learningcenter.security.domain.service.communication.RegisterResponse;
import com.acme.learningcenter.security.middleware.JwtHandler;
import com.acme.learningcenter.security.middleware.UserDetailsImpl;
import com.acme.learningcenter.security.resource.AuthenticateResource;
import com.acme.learningcenter.security.resource.UserResource;
import com.acme.learningcenter.shared.mapping.EnhancedModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtHandler handler;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private EnhancedModelMapper mapper;

  @Override
  public ResponseEntity<?> authenticate(AuthenticateRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = handler.generateToken(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
      AuthenticateResource resource = mapper.map(userDetails, AuthenticateResource.class);
      resource.setToken(token);
      resource.setRoles(roles);
      AuthenticateResponse response = new AuthenticateResponse(resource);
      return ResponseEntity.ok(response.getResource());
    } catch (Exception e) {
      AuthenticateResponse response = new AuthenticateResponse(
        String.format("An error occurred while authenticating: %s", e.getMessage()));
      return ResponseEntity.badRequest().body(response.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      AuthenticateResponse response = new AuthenticateResponse("Username is already used.");
      return ResponseEntity.badRequest().body(response.getMessage());
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      AuthenticateResponse response = new AuthenticateResponse("Email is already used.");
      return ResponseEntity.badRequest().body(response.getMessage());
    }
    try {
      Set<String> rolesStringSet = request.getRoles();
      Set<Role> roles = new HashSet<>();
      if (rolesStringSet == null) {
        roleRepository.findByName(Roles.ROLE_USER)
          .map(roles::add)
          .orElseThrow(() -> new RuntimeException("Role not found."));
      } else {
        rolesStringSet.forEach(roleString ->
          roleRepository.findByName(Roles.valueOf(roleString))
            .map(roles::add)
            .orElseThrow(() -> new RuntimeException("Role not found")));
      }
      logger.info("Roles: {}", roles);

      User user = new User()
        .withUsername(request.getUsername())
        .withEmail(request.getEmail())
        .withPassword(encoder.encode(request.getPassword()))
        .withRoles(roles);
      userRepository.save(user);
      UserResource resource = mapper.map(user, UserResource.class);
      RegisterResponse response = new RegisterResponse(resource);
      return ResponseEntity.ok(response.getResource());
    } catch (Exception e) {
      RegisterResponse response = new RegisterResponse(e.getMessage());
      return ResponseEntity.badRequest().body(response.getMessage());
    }
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));
    return UserDetailsImpl.build(user);
  }
}
