package com.gov.security.config;

import com.gov.security.Service.MyUserAuthenticationConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Created by: 李浩洋 on 2019-10-29
 *
 * 认证服务器
 **/
@Configuration  //这是一个配置类
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {//AuthorizationServerConfigurerAdapter：认证服务器适配器


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;
//
//    @Bean
//    public TokenStore getTokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource oauthDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    /**
     * 1，配置客户端应用的信息，让认证服务器知道有哪些客户端应用来申请令牌。
     *
     * ClientDetailsServiceConfigurer：客户端的详情服务的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    /**
     *,2，配置用户信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        //传给他一个authenticationManager用来校验传过来的用户信息是不是合法的,注进来一个，自己实现
        //endpoints.authenticationManager(authenticationManager);
//        endpoints.tokenStore(getTokenStore());
//
//        // 配置TokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
//        endpoints.tokenServices(tokenServices);


        endpoints
            .authenticationManager(authenticationManager)
            .approvalStore(approvalStore())
            .authorizationCodeServices(authorizationCodeServices())
            //.accessTokenConverter(defaultAccessTokenConverter)
            .tokenStore(tokenStore());
//            .tokenEnhancer((accessToken, authentication) ->{
//                DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
//                if(defaultOAuth2AccessToken.getAdditionalInformation()==null || defaultOAuth2AccessToken.getAdditionalInformation().size()<1){
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("type",1);
//                    defaultOAuth2AccessToken.setAdditionalInformation(map);
//                    defaultOAuth2AccessToken.
//                }
//                return accessToken;
//            });
    }


    /**
     * 3，配置资源服务器过来验token 的规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        /**
//         * 过来验令牌有效性的请求，不是谁都能验的，必须要是经过身份认证的。
//         * 所谓身份认证就是，必须携带clientId，clientSecret，否则随便一请求过来验token是不验的
//         */
//        //.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
        security.checkTokenAccess("permitAll()");
        security.allowFormAuthenticationForClients();

    }
}
