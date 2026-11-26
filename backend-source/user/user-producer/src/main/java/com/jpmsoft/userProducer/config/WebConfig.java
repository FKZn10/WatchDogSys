package com.jpmsoft.userProducer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 9:27
 */
//@Configuration
public class WebConfig  implements WebMvcConfigurer {

//    @Resource
//    private TenantDsInterceptor tenantDsInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册租户切换数据源拦截器
//        registry.addInterceptor(this.tenantDsInterceptor);
//    }
}
