package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:58
 */
@Slf4j
@RestController
@Api(tags = "合同支付现场检测关联表", value = "合同支付现场检测关联表")
@RequestMapping("/v1/conPayWitness")
public class ConPayWitnessController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "支付ID查询合同支付现场检测关联表")
    @GetMapping("/getConPayWitnessByPayId")
    public BaseResponse getConPayWitnessByPayId(@RequestParam String payId) {
        log.info("getConPayWitnessByPayId");
        return conFeign.getConPayWitnessByPayId(payId);
    }

    @ApiOperation(value = "委托单编号查询合同支付现场检测关联表")
    @GetMapping("/getConPayWitnessByOrderNo")
    public BaseResponse getConPayWitnessByOrderNo(@RequestParam String orderNo) {
        log.info("getConPayWitnessByOrderNo");
        return conFeign.getConPayWitnessByOrderNo(orderNo);
    }

    @ApiOperation(value = "合同ID查询合同支付现场检测关联表")
    @GetMapping("/getConPayWitnessByConId")
    public BaseResponse getConPayWitnessByConId(@RequestParam String conId) {
        log.info("getConPayWitnessByConId");
        return conFeign.getConPayWitnessByConId(conId);
    }
}
