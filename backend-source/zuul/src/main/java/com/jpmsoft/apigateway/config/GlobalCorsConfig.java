package com.jpmsoft.apigateway.config;

import com.jpmsoft.apigateway.utils.TxtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * 全局跨域配置
 */
@Configuration
public class GlobalCorsConfig {

    private final static List<String> allowedOrigins = TxtUtil.readTxtFile("InterfaceFilter/Cors_Filter.txt", "UTF-8");


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        allowedOrigins.forEach(origins -> {
            System.out.println("允许的域：" + origins);
            corsConfiguration.addAllowedOrigin(origins); // 允许的域名
        });
        // 允许的head头部
        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedHeader("token");
//        corsConfiguration.addAllowedHeader("Accept");
//        corsConfiguration.addAllowedHeader("Content-Type");
//        corsConfiguration.addAllowedHeader("Origin");
        // 允许的请求方法
//        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(600L);
        return corsConfiguration;
    }

    // 添加CorsFilter拦截器，对任意的请求使用
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
