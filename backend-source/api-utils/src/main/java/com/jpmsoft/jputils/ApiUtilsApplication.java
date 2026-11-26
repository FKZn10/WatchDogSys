package com.jpmsoft.jputils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ApiUtilsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ApiUtilsApplication.class, args);
    }
}
