package com.jpmsoft.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;


/**
 * 禁用Swagger静态资源访问
 *
 * @author Aaron
 * @date 2021-05-16
 */
@Configuration
public class SwaggerDisableConfig implements WebMvcConfigurer {

    @Value("${swagger.enable:false}")
    private boolean enableSwagger;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!enableSwagger) {
            // 禁用所有Swagger相关资源
            registry.addResourceHandler("/webjars/springfox-swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                    .resourceChain(false)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath, Resource location) throws IOException {
                            // 返回空资源，阻止访问
                            return null;
                        }
                    });
        }
    }
}
