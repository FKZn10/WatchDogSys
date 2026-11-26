package com.jpmsoft.userProducer;

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

@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.jpmsoft.userProducer.mapper")
@SpringBootApplication(scanBasePackages = {"com.jpmsoft.userProducer", "com.jpmsoft.common"})
public class UserProducerApplication extends SpringBootServletInitializer {

    //    private static LicenseUtil licenseUtil;
//
//    @Resource
//    public void setLicenseUtil(LicenseUtil licenseUtil) {
//        UserProducerApplication.licenseUtil = licenseUtil;
//    }
//
    public static void main(String[] args) {
        SpringApplication.run(UserProducerApplication.class, args);
//        licenseUtil.check();
    }

    /**
     * 服务内部负载调用
     *
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 调用外部接口
     *
     * @return
     */
    @Bean
    RestTemplate restTemplateApi() {
        return new RestTemplate();
    }
}

