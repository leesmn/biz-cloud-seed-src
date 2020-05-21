package com.nb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class NbServerOauthApplication {


	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(NbServerOauthApplication.class, args);
	}



}
