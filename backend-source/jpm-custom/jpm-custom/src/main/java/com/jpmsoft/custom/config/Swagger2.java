package com.jpmsoft.custom.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        /*添加接口请求头参数配置 没有的话 可以忽略*/
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jpmsoft.custom.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(globalRequestParameters());
    }

    private List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
                //每次请求加载header
                .in(ParameterType.HEADER)
                //头标签
                .name("token")
                .required(false)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)).defaultValue("f3be04d879dd467387a3a74385afa771"));
        return Collections.singletonList(parameterBuilder.build());
    }

    /**
     * 构建 api文档的详细信息函数
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JPM4.0 相关接口")
                .contact(new Contact("建普软件有限公司", "http://www.jpmsoft.com/", ""))
                .version("1.0")
                .description("API 描述")
                .build();
    }
}
