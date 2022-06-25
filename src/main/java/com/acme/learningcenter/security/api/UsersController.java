package com.acme.learningcenter.security.api;

import com.acme.learningcenter.security.domain.service.UserService;
import com.acme.learningcenter.security.domain.service.communication.AuthenticateRequest;
import com.acme.learningcenter.security.domain.service.communication.RegisterRequest;
import com.acme.learningcenter.security.mapping.UserMapper;
import com.acme.learningcenter.security.resource.UserResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Users", description = "Create, read, update and delete users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
  private final UserService userService;
  private final UserMapper mapper;

  public UsersController(UserService userService, UserMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }

  @PostMapping("/auth/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateRequest request) {
    return userService.authenticate(request);
  }

  @PostMapping("/auth/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
    return userService.register(request);
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllUsers(Pageable pageable) {
    Page<UserResource> resources = mapper.modelListToPage(userService.getAll(), pageable);
    return ResponseEntity.ok(resources);
  }
}
