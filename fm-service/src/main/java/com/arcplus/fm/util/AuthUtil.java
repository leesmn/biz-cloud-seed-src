package com.arcplus.fm.util;

import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class AuthUtil {
  public static Map<String, Object> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof OAuth2Authentication) {
      OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
      authentication = oAuth2Auth.getUserAuthentication();
      if (authentication instanceof UsernamePasswordAuthenticationToken) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        Map map = (Map) authenticationToken.getDetails();
        map = (Map) map.get("principal");
        return map;
      }
    }
    return null;
  }


  public static int GetCurrentUID() {
    Map map = AuthUtil.getCurrentUser();
    return (int) map.get("id");
  }

  public static String GetCurrentUserName() {
    Map map = AuthUtil.getCurrentUser();
    return map.get("username").toString();
  }
}