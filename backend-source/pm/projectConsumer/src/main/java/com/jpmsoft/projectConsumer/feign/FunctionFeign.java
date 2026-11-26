package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "project-producer", contextId = "functionTimeConfig")
public interface FunctionFeign {

    @PostMapping("/v1/functionTimeConfig/createFunctionTimeConfig")
    BaseResponse createFunctionTimeConfig(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/functionTimeConfig/updateFunctionTimeConfigAllField")
    BaseResponse updateFunctionTimeConfigAllField(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/functionTimeConfig/getFunctionTimeConfigInfoById")
    BaseResponse getFunctionTimeConfigInfoById(@RequestBody RequestBean requestBean);


    @PostMapping("/v1/functionTimeConfig/getFunctionTimeConfigPageList")
    BaseResponse getFunctionTimeConfigPageList(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/functionTimeConfig/deleteFunctionTimeConfigLogicalSingle")
    BaseResponse deleteFunctionTimeConfigLogicalSingle(@RequestBody RequestBean requestBean);
}
