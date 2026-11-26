package com.jpmsoft.projectConsumer.controller.v1.function;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.FunctionFeign;
import com.jpmsoft.common.utils.RequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private FunctionFeign functionFeign;


    @PostMapping("/createFunctionTimeConfig")
    public BaseResponse createFunctionTimeConfig(@RequestBody RequestBean requestBean) {
        try {
            log.info("createFunctionTimeConfig");
            return functionFeign.createFunctionTimeConfig(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    @PostMapping("/updateFunctionTimeConfigAllField")
    public BaseResponse updateFunctionTimeConfigAllField(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateFunctionTimeConfigAllField");
            return functionFeign.updateFunctionTimeConfigAllField(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    @PostMapping("/getFunctionTimeConfigInfoById")
    public BaseResponse getFunctionTimeConfigInfoById(@RequestBody RequestBean requestBean) {
        try {
            log.info("getFunctionTimeConfigInfoById");
            return functionFeign.getFunctionTimeConfigInfoById(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    @PostMapping("/getFunctionTimeConfigPageList")
    public BaseResponse getFunctionTimeConfigPageList(@RequestBody RequestBean requestBean) {
        try {
            log.info("getFunctionTimeConfigPageList");
            return functionFeign.getFunctionTimeConfigPageList(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    @PostMapping("/deleteFunctionTimeConfigLogicalSingle")
    public BaseResponse deleteFunctionTimeConfigLogicalSingle(@RequestBody RequestBean requestBean) {
        try {
            log.info("deleteFunctionTimeConfigLogicalSingle");
            return functionFeign.deleteFunctionTimeConfigLogicalSingle(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }
}
