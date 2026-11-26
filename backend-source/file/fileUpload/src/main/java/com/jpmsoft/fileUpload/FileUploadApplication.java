package com.jpmsoft.fileUpload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableFeignClients
@EnableScheduling
@EnableEurekaClient
@MapperScan("com.jpmsoft.fileUpload.mapper")
@SpringBootApplication(scanBasePackages = {"com.jpmsoft.fileUpload", "com.jpmsoft.common"})
public class FileUploadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    RestTemplate restTemplateApi() {
        return new RestTemplate();
    }
}
