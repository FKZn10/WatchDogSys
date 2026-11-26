package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConMeasurePriceRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合同计量计价 控制器
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Slf4j
@RestController
@RequestMapping("/v1/conMeasurePrice")
@Api(tags = "合同计量计价", value = "合同计量计价")
public class ConMeasurePriceController {
    @Resource
    private ConFeign conFeign;

    /**
     * 创建或更新合同计量计价
     *
     * @param request
     * @return
     */
    @ApiOperation("创建或更新合同计量计价")
    @PostMapping("/createOrUpdateConMeasurePrice")
    public BaseResponse createOrUpdateConMeasurePrice(@RequestBody ConMeasurePriceRequest request) {
        log.info("createOrUpdateConMeasurePrice");
        return conFeign.createOrUpdateConMeasurePrice(request);
    }

    /**
     * 获取合同计量计价列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取合同计量计价列表")
    @PostMapping("/getConMeasurePriceList")
    public BaseResponse getConMeasurePriceList(@RequestBody ConMeasurePriceRequest request, @RequestHeader String token) {
        log.info("getConMeasurePriceList");
        return conFeign.getConMeasurePriceList(request, token);
    }

    /**
     * 获取合同计量计价列表（不鉴权）
     *
     * @param request
     * @return
     */
    @PostMapping("/getConMeasurePriceListNoAuth")
    public BaseResponse getConMeasurePriceListNoAuth(@RequestBody ConMeasurePriceRequest request) {
        log.info("getConMeasurePriceListNoAuth");
        return conFeign.getConMeasurePriceListNoAuth(request);
    }

    /**
     * 获取合同计量计价详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取合同计量计价详情")
    @GetMapping("/getConMeasurePriceById")
    public BaseResponse getConMeasurePriceById(@RequestParam String id) {
        log.info("getConMeasurePriceById");
        return conFeign.getConMeasurePriceById(id);
    }

    /**
     * 通过合同ID获取合同计量计价信息
     *
     * @param conId
     * @param id
     * @return
     */
    @ApiOperation("通过合同ID获取合同计量计价信息")
    @GetMapping("/getConMeasurePriceInfoByConId")
    public BaseResponse getConMeasurePriceInfoByConId(@RequestParam("conId") String conId, @RequestParam(value = "id", required = false) String id) {
        log.info("getConMeasurePriceInfoByConId");
        return conFeign.getConMeasurePriceInfoByConId(conId, id);
    }
}