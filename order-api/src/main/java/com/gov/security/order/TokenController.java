package com.gov.security.order;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
  @Autowired
  OAuth2RestTemplate oAuth2RestTemplate;

  @RequestMapping("/user/token")
  public OAuth2AccessToken userToken(@RequestParam Map<String, String> parameters) {
//    parameters.put("grant_type","password");
//    parameters.put("client_id","orderService");
//    parameters.put("client_secret","123456");//scope
//    parameters.put("scope","read");
    return oAuth2RestTemplate.getAccessToken();//postAccessToken(parameters);
  }
}
