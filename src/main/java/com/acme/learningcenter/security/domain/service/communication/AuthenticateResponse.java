package com.acme.learningcenter.security.domain.service.communication;

import com.acme.learningcenter.shared.domain.service.communication.BaseResponse;

public class AuthenticateResponse  extends BaseResponse<AuthenticateRequest> {
  public AuthenticateResponse(String message) {
    super(message);
  }

  public AuthenticateResponse(AuthenticateRequest resource) {
    super(resource);
  }
}
