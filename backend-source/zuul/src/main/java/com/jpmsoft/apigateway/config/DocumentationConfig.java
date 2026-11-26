package com.jpmsoft.apigateway.config;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("Base", "/api/user", "1.0"));
        resources.add(swaggerResource("PM", "/pm/pm", "1.0"));
        resources.add(swaggerResource("BPM", "/jpbpm/bpm", "1.0"));
        resources.add(swaggerResource("FileServer", "/fileServer/file", "1.0"));
        resources.add(swaggerResource("Api-Utils", "/common/utils", "1.0"));
        resources.add(swaggerResource("RunBath", "/bath/bath", "1.0"));
        resources.add(swaggerResource("Open-File", "/openFile/openFile", "1.0"));
        resources.add(swaggerResource("Cockpit", "/cockpit/cockpit", "1.0"));
        resources.add(swaggerResource("intelligentEvaluate", "/intelligentEvaluate/intelligentEvaluate", "1.0"));
        resources.add(swaggerResource("designReview", "/designReview/designReview", "1.0"));
        resources.add(swaggerResource("营运接口", "/operate/operate", "1.0"));
        resources.add(swaggerResource("对接服务", "/jpmDock/dock", "1.0"));
        resources.add(swaggerResource("Jpm-AI", "/ai/ai", "1.0"));
        resources.add(swaggerResource("智能建造展厅门户", "/ihome/intelligent-home", "1.0"));
        return resources;
    }


    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
