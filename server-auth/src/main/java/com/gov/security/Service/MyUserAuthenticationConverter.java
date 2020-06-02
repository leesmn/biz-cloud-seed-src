package com.gov.security.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Service;

@Service
public class MyUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

  @Override
  public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    Map<String, Object> response = new LinkedHashMap();
    response.put("user_name", authentication);
    return response;
  }
}

