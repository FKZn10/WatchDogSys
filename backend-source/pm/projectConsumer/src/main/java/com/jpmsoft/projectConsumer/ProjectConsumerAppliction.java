package com.jpmsoft.projectConsumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@EnableHystrix
@SpringBootApplication(scanBasePackages = {"com.jpmsoft.projectConsumer", "com.jpmsoft.common"})
public class ProjectConsumerAppliction extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ProjectConsumerAppliction.class, args);
    }
}
