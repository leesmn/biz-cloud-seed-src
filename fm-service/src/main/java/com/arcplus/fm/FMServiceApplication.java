package com.arcplus.fm;

import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 用户中心
 * 
 * @author 华建盛裕 tecservice@aifmworld.com
 *
 */
//@EnableHystrix
//@EnableCircuitBreaker
//@EnableHystrixDashboard
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FMServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FMServiceApplication.class, args);
	}


	/**
	 * 监听器：监听HTTP请求事件
	 * 解决RequestContextHolder.getRequestAttributes()空指针问题
	 * @return
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		new Date();
		return new RequestContextListener();
	}
}