package com.jpmsoft.fileUpload.config;

import com.bes.enterprise.springboot.embedded.BesServletWebServerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 */
@Configuration
public class ServletTypeContainerConfig {

    @Value("${static.servletType}")
    private String servletType;

    @Bean
    public ServletWebServerFactory tomcatServletWebServerFactory() {
        if (servletType.equalsIgnoreCase("BES")) {
            //宝兰德AppServer
            return new BesServletWebServerFactory();
        }
        //tomcat
        return new TomcatServletWebServerFactory();
    }
}
