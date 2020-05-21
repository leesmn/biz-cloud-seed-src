//package com.arcplus.fm.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig  {
//    //两种方式任选其一即可
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // 拦截所有权请求
//                        .allowedMethods("*") // 允许提交请求的方法，*表示全部允许
//                        .allowedOrigins("*") // #允许向该服务器提交请求的URI，*表示全部允许
//                        .allowCredentials(true) // 允许cookies跨域
//                        .allowedHeaders("*") // #允许访问的头信息,*表示全部
//                        .maxAge(18000L); // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//            }
//        };
//    }
//}