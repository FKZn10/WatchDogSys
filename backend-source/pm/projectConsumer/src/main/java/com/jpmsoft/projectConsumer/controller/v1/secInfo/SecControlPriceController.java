package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPriceRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标控制价", value = "招标控制价")
@RequestMapping("/v1/controlPrice")
public class SecControlPriceController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标控制价")
    @PostMapping("/createOrUpdateControlPrice")
    public BaseResponse createOrUpdateControlPrice(@RequestBody SecControlPriceRequest request) {
        log.info("createOrUpdateControlPrice");
        return secFeign.createOrUpdateControlPrice(request);
    }

    @ApiOperation(value = "ID删除招标控制价")
    @GetMapping("/deleteControlPriceBycpId")
    public BaseResponse deleteControlPriceBycpId(@RequestParam String cpId) {
        log.info("deleteControlPriceBycpId");
        return secFeign.deleteControlPriceBycpId(cpId);
    }

    @ApiOperation(value = "ID查询招标控制价")
    @GetMapping("/getControlPriceBycpId")
    public BaseResponse getControlPriceBycpId(@RequestParam String cpId) {
        log.info("getControlPriceBycpId");
        return secFeign.getControlPriceBycpId(cpId);
    }

    @ApiOperation(value = "分页查询招标控制价")
    @PostMapping("/getControlPriceList")
    public BaseResponse getControlPriceList(@RequestBody SecControlPriceRequest request) {
        log.info("getControlPriceList");
        return secFeign.getControlPriceList(request);
    }

    @ApiOperation(value = "根据标段ID获取最新的招标控制价")
    @GetMapping("/getControlPriceBysecId")
    public BaseResponse getControlPriceBysecId(@RequestParam String secId) {
        log.info("getControlPriceBysecId");
        return secFeign.getControlPriceBysecId(secId);
    }
}
