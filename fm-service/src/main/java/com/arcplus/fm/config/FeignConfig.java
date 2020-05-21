package com.arcplus.fm.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
public class FeignConfig{

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if(attributes!=null){
                    HttpServletRequest request = attributes.getRequest();
                    Enumeration<String> headerNames = request.getHeaderNames();
                    boolean isAddAuthorization = false;
                    if (headerNames != null) {
                        while (headerNames.hasMoreElements()) {
                            String name = headerNames.nextElement();
                            String values = request.getHeader(name);
                            if(name.equals("Authorization")){
                                isAddAuthorization = true;
                            }
                            template.header(name, values);
                        }
                    }
                    if( request.getParameter("access_token")!=null && !isAddAuthorization){
                        template.header("Authorization", "Bearer  " + request.getParameter("access_token"));
                    }
                }
            }
        };
        return requestInterceptor;
    }
//
//    /**
//     * 配置请求重试
//     *
//     */
//    @Bean
//    public Retryer feignRetryer() {
//        return new Retryer.Default(200, SECONDS.toMillis(2), 10);
//    }
//
//
//    /**
//     * 设置请求超时时间
//     *默认
//     * public Options() {
//     * this(10 * 1000, 60 * 1000);
//     * }
//     *
//     */
//    @Bean
//    Request.Options feignOptions() {
//        return new Request.Options(60 * 1000, 60 * 1000);
//    }
//
//
//
//    /**
//     * 打印请求日志
//     * @return
//     */
//    @Bean
//    public feign.Logger.Level multipartLoggerLevel() {
//        return feign.Logger.Level.FULL;
//    }
}