package com.arcplus.fm.thirdparty.bos;

import com.arcplus.fm.common.ServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@PropertySource("classpath:app.properties")
public class BosAuthService {

  private static final String ACCESS_BOS_TOKEN = "ACCESS_BOS_TOKEN";
  @Value("${bos.url}")
  private String baseUrl;

  @Value("${bos.appid}")
  private String appid;

  @Value("${bos.secret}")
  private String secret;

  public String getToken() {
    if (!redisTemplate.hasKey(ACCESS_BOS_TOKEN)) {
      return genBosToken();
    }
    log.info("get bos access token from cach:"+ redisTemplate.opsForValue().get(ACCESS_BOS_TOKEN));
    return redisTemplate.opsForValue().get(ACCESS_BOS_TOKEN);
  }

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private RestTemplate restTemplate;


  private String genBosToken() {
    String new_access_token = "";
    Integer expires_in = 7199;
    /*todo*/
    String bos_url = baseUrl + "/open/token?appId="+ appid +"&secret="+secret;
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("appId",appid);
    paramMap.put("secret",secret);
    try{
        ResponseEntity<Map> response  = restTemplate.getForEntity(bos_url, Map.class);
        if(response.getStatusCode().is2xxSuccessful()){
          Map<String,Object> result = response.getBody();
          Integer code = Integer.parseInt(result.get("code").toString());
          if(!code.equals(200)){
            String msg = result.get("message").toString();
            throw new ServiceException(code,"bos接口返回错误,"+msg);
          }
          new_access_token = ((Map)result.get("data")).get("access_token").toString();
          expires_in = Integer.parseInt(((Map)result.get("data")).get("expires_in").toString());
        }else{
          throw new ServiceException(500,"bos接口返回非成功状态码");
        }
    }catch (Exception e){
      e.printStackTrace();
        throw new ServiceException(500,"bos接口内部错误");

    }

    log.info("gen bos access token:"+ new_access_token);
    redisTemplate.opsForValue().set(ACCESS_BOS_TOKEN, new_access_token,expires_in, TimeUnit.SECONDS);
    return new_access_token;
  }
}
