package com.jpmsoft.projectProducer.controller.v1.function;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.service.function.impl.FunctionTimeConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 功能时间配置 前端控制器
 * </p>
 *
 * @author zhangl
 * @since 2022-08-01
 */
@Slf4j
@RestController
@RequestMapping("/v1/functionTimeConfig")
public class FunctionTimeConfigController {
    @Resource
    private FunctionTimeConfigServiceImpl functionTimeConfigService;


    @PostMapping("/createFunctionTimeConfig")
    public BaseResponse createFunctionTimeConfig(@RequestBody RequestBean requestBean) {
        log.info("createFunctionTimeConfig");
        return functionTimeConfigService.createFunctionTimeConfig(requestBean);
    }

    @PostMapping("/updateFunctionTimeConfigAllField")
    public BaseResponse updateFunctionTimeConfigAllField(@RequestBody RequestBean requestBean) {
        log.info("updateFunctionTimeConfigAllField");
        return functionTimeConfigService.updateFunctionTimeConfigAllField(requestBean);
    }

    @PostMapping("/getFunctionTimeConfigInfoById")
    public BaseResponse getFunctionTimeConfigInfoById(@RequestBody RequestBean requestBean) {
        log.info("getFunctionTimeConfigInfoById");
        return functionTimeConfigService.getFunctionTimeConfigInfoById(requestBean);
    }

    @PostMapping("/getFunctionTimeConfigPageList")
    public BaseResponse getFunctionTimeConfigPageList(@RequestBody RequestBean requestBean) {
        log.info("getFunctionTimeConfigPageList");
        return functionTimeConfigService.getFunctionTimeConfigPageList(requestBean);
    }

    @PostMapping("/deleteFunctionTimeConfigLogicalSingle")
    public BaseResponse deleteFunctionTimeConfigLogicalSingle(@RequestBody RequestBean requestBean) {
        log.info("deleteFunctionTimeConfigLogicalSingle");
        return functionTimeConfigService.deleteFunctionTimeConfigLogicalSingle(requestBean);
    }
}
