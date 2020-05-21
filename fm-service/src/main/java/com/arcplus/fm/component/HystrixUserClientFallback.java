package com.arcplus.fm.component;

import com.arcplus.fm.feign.UserClient;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HystrixUserClientFallback  implements UserClient {

  @Override
  public List<Map> getUserByType(int jobType) {
    return null;
  }

  @Override
  public Map getUserById(int id) {
    return null;
  }

  @Override
  public Object getUserInfo(int id) {
    return null;
  }
}
