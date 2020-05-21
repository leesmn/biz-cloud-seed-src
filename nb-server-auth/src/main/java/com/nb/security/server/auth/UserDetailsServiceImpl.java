package com.nb.security.server.auth;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by: 李浩洋 on 2019-10-29
 **/
@Component//TODO:这里不写 ("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     *
     */
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        return User.withUsername(username)
//                .password("$2a$10$U2QYW9P2F5Q9Eai1CP9px.GPdzWEBFzNXRfjaa9uCIIVyr/yABroC")
//                .authorities("ROLE_ADMIN") //权限
//                .build();//构建一个User对象
        return new User(username, bCryptPasswordEncoder.encode("123"), getAuthority());

    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
