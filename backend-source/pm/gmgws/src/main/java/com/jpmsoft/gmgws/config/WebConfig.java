package com.jpmsoft.gmgws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 9:27
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {

//    @Resource
//    private TenantDsInterceptor tenantDsInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册租户切换数据源拦截器
//        registry.addInterceptor(this.tenantDsInterceptor);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");
        // 配置静态文件访问路径
//        registry
//                .addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/")
//                .setCachePeriod(2592000)
//                .resourceChain(true)
//                .addResolver(versionResourceResolver);
        // 解决swagger无法访问
        registry
                .addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry
                .addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

    }
}
