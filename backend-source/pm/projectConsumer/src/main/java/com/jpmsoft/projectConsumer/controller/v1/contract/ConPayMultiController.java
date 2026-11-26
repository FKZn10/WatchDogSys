package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConPayMultiRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPayMulti")
@Api(tags = "多项目合同支付", value = "多项目合同支付")
public class ConPayMultiController {
    @Resource
    private ConFeign conFeign;

    /**
     * 同步支付信息到合同支付表
     *
     * @param request
     * @return
     */
    @ApiOperation("同步支付信息到合同支付表")
    @PostMapping("/syncToConPay")
    public BaseResponse syncToConPay(@RequestBody ConPayMultiRequest request) {
        log.info("syncToConPay");
        return conFeign.syncToConPay(request);
    }

    /**
     * 根据ID获取多项目合同支付信息
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID获取多项目合同支付信息")
    @GetMapping("/getConPayMultiById")
    public BaseResponse getConPayMultiById(@RequestParam("id") String id) {
        log.info("getConPayMultiById");
        return conFeign.getConPayMultiById(id);
    }

    /**
     * 多项目合同支付分页查询
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("多项目合同支付分页查询")
    @PostMapping("/getConPayMultiPage")
    public BaseResponse getConPayMultiPage(@RequestBody ConPayMultiRequest request, @RequestHeader String token) {
        log.info("getConPayMultiPage");
        return conFeign.getConPayMultiPage(request, token);
    }
}