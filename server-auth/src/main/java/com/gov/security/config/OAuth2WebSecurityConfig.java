package com.gov.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Created by: 李浩洋 on 2019-10-29
 **/
@Configuration
@EnableWebSecurity //使安全配置生效
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }
    /**
     * AuthenticationManagerBuilder 是用来构建  AuthenticationManager（处理登录操作）的
     * 需要两个东西：userDetailsService  、passwordEncoder
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService) //获取用户信息
//                .passwordEncoder(bCryptPasswordEncoder); //比对密码
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 把AuthenticationManager暴露为bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/user/token","/client/token").permitAll().anyRequest().authenticated();


//        http .authorizeRequests().antMatchers("/login").permitAll().and()
//            // default protection for all resources (including /oauth/authorize)
//            .authorizeRequests() .anyRequest().hasRole("USER")
//        // ... more configuration, e.g. for form login

//        // 禁用CSRF
//        http.csrf().disable();

    }


//    /**
//     * 通过这个Bean，去远程调用认证服务器，验token
//     * @return
//     */
//    @Bean
//    public ResourceServerTokenServices tokenServices(){
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setClientId("orderService");//在认证服务器配置的，订单服务的clientId
//        tokenServices.setClientSecret("123456");//在认证服务器配置的，订单服务的ClientSecret
//        tokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
//        return tokenServices;
//    }
//
//
//    /**
//     * 要认证跟用户相关的信息，一般用 AuthenticationManager
//     * 覆盖这个方法，可以将AuthenticationManager暴露为一个Bean
//     *
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//        authenticationManager.setTokenServices(tokenServices());//设置为自定义的TokenServices，去校验令牌
//        return authenticationManager;
//    }
}
