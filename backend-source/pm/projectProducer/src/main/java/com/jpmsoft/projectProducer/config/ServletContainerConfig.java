package com.jpmsoft.projectProducer.config;

// import com.apusic.boot.web.embedded.ams.AasServletWebServerFactory;
import com.bes.enterprise.springboot.embedded.BesServletWebServerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet容器选择
 *
 * @Author: 李文涛 aaron
 * @date: 2024/7/26 10:53
 */
@Configuration
public class ServletContainerConfig {

    @Value("${static.servletType}")
    private String servletType;

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        if (servletType.equalsIgnoreCase("BES")) //宝兰德AppServer
            return new BesServletWebServerFactory();
        //if (servletType.equalsIgnoreCase("AAS")) //金蝶天燕AAS
            //return new AasServletWebServerFactory();
        return new TomcatServletWebServerFactory();//tomcat
    }
}
