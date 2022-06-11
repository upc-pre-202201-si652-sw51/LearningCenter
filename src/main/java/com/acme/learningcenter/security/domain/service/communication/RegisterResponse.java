package com.acme.learningcenter.security.domain.service.communication;

import com.acme.learningcenter.security.resource.UserResource;
import com.acme.learningcenter.shared.domain.service.communication.BaseResponse;

public class RegisterResponse extends BaseResponse<UserResource> {
  public RegisterResponse(String message) {
    super(message);
  }

  public RegisterResponse(UserResource resource) {
    super(resource);
  }
}
