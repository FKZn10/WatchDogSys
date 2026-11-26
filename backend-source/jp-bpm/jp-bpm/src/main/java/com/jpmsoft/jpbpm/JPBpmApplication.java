package com.jpmsoft.jpbpm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

//@EnableDiscoveryClient
@EnableEurekaClient
@EnableScheduling  //加上这个注解
@MapperScan("com.jpmsoft.jpbpm.mapper")
@SpringBootApplication(scanBasePackages = {"com.jpmsoft.jpbpm", "com.jpmsoft.common"})
public class JPBpmApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(JPBpmApplication.class, args);
    }


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}
