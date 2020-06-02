package com.gov.security.order;

import com.alibaba.fastjson.JSON;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * @AuthenticationPrincipal 注解可以获取生成token的用户名
     * @param info
     * @param username
     * @return
     */
    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @AuthenticationPrincipal String username){
        log.info("获取到username = {}",username);
        //查询价格
//        PriceInfo price = restTemplate.getForObject("http://localhost:9080/prices/"+info.getProductId(),PriceInfo.class);
//        log.info("price is "+price.getPrice());
        return info;
    }


    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id){
        log.info("getInfo: id is "+id);
        return new OrderInfo(id);
    }

    /**
     * 取认证用户信息
     * @return
     */
    @RequestMapping({ "/client/userinfo" })
    public Principal user(Principal user) {
        if (!redisTemplate.hasKey(user.getName())) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",1);
            map.put("type",2);
            redisTemplate.opsForValue().set(user.getName(), JSON.toJSONString(map),1000, TimeUnit.SECONDS);
       }
        if(user!=null && user instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) user;
            UsernamePasswordAuthenticationToken userAuthentication = (UsernamePasswordAuthenticationToken)oAuth2Authentication.getUserAuthentication();
            userAuthentication.setDetails(redisTemplate.opsForValue().get(user.getName()));
        }
        return user;
    }

}
