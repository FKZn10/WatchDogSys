package com.jpmsoft.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//@EnableAdminServer
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(EurekaServerApplication.class);
//    }
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

//    @EnableWebSecurity
//    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            //方法1：关闭csrf
//            http.csrf().disable();
//            //方法2：忽略/eureka/** 所有请求
//            http.csrf().ignoringAntMatchers("/eureka/**");
//            super.configure(http);
//        }
//    }
}
