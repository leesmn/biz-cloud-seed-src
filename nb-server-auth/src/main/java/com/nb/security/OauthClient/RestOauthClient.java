package com.nb.security.OauthClient;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Component
public class RestOauthClient {
    @Autowired
    RestTemplate restTemplate;

    public Map<String, Object> postAccessToken(Map<String, String> parameters){

      try {
        String url = "";
        if(parameters.get("grant_type").equals("password")){
          url = "http://localhost:9090/oauth/token?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}&scope={scope}&username={username}&password={password}";
        }else{
          url = "http://localhost:9090/oauth/token?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}";
        }
        ResponseEntity<Map> response = restTemplate.exchange(
            url ,
            HttpMethod.POST,
            new HttpEntity<String>(new HttpHeaders()),
            Map.class,
            parameters);
        Map<String,Object> body = response.getBody();
        return body;
      }catch (Exception e){
        e.printStackTrace();
      }
      Map<String,Object> failResult = new HashMap<>();
      failResult.put("code",500);
      failResult.put("msg","get token error");
      return failResult;
    }

  public Map<String, Object> checkAccessToken(Map<String, String> parameters){

    try {
      String url = "";

      url = "http://10.252.29.152:9090/oauth/check_token?token={token}";

      ResponseEntity<Map> response = restTemplate.exchange(
          url ,
          HttpMethod.POST,
          new HttpEntity<String>(new HttpHeaders()),
          Map.class,
          parameters);
      Map<String,Object> body = response.getBody();
      return body;
    }catch (Exception e){
      e.printStackTrace();
    }
    Map<String,Object> failResult = new HashMap<>();
    failResult.put("code",500);
    failResult.put("msg","check token error");
    return failResult;
  }

}
