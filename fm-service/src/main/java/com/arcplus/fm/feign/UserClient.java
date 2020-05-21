package com.arcplus.fm.feign;

import com.arcplus.fm.component.HystrixUserClientFallback;
import com.arcplus.fm.entity.dto.LoginUserDto;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "jv-system-center-${spring.profiles.active}",fallback = HystrixUserClientFallback.class)
public interface UserClient {

    @GetMapping(path = "users/findUserByJobType/{jobType}")
    List<Map> getUserByType(@PathVariable("jobType") int jobType);

    @RequestMapping(value = "users/findUserById/{id}", method = RequestMethod.GET)
    Map getUserById(@PathVariable("id") int id);


    @GetMapping(value = "users-anon/{id}")
    Object getUserInfo(@PathVariable("id") int id);

}
