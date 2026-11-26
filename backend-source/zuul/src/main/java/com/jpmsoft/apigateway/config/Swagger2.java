package com.jpmsoft.apigateway.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 *
 * @author Aaron
 * @date 2021-05-16
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.enable(true).apiInfo(apiInfo());
        docket.select();
        return docket;
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
