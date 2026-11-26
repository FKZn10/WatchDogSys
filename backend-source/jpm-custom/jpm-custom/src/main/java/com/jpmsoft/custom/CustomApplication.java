package com.jpmsoft.custom;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.jpmsoft.custom.mapper")
@SpringBootApplication(scanBasePackages = {"com.jpmsoft.custom", "com.jpmsoft.common"})
public class CustomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CustomApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
