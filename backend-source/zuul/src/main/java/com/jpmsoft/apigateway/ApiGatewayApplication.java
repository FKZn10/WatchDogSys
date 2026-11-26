package com.jpmsoft.apigateway;

import com.jpmsoft.apigateway.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication extends SpringBootServletInitializer {

    @Autowired
    private RedisUtils redisUtils;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    /**
//     * 生成系统token
//     * 方便于swagger使用
//     */
//    @Bean
//    public void setAdminToken() {
//        redisUtils.set("f3be04d879dd467387a3a74385afa771", "68D319413F904195A859973BD4C5702D");
//        redisUtils.set("firstLogin_f3be04d879dd467387a3a74385afa771", 0);
//    }
}
