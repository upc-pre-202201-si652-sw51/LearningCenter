package com.acme.learningcenter.security.domain.service.communication;

import com.acme.learningcenter.security.resource.AuthenticateResource;
import com.acme.learningcenter.shared.domain.service.communication.BaseResponse;

public class AuthenticateResponse  extends BaseResponse<AuthenticateResource> {
  public AuthenticateResponse(String message) {
    super(message);
  }

  public AuthenticateResponse(AuthenticateResource resource) {
    super(resource);
  }
}
