package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProPurchaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "采购意向表", value = "采购意向表")
@RequestMapping("/v1/propruchase")
public class ProPurchaseController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "创建或修改采购意向表")
    @PostMapping("/createOrUpdatePropurchase")
    public BaseResponse createOrUpdatePropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pc createOrUpdatePropurchase");
        return preFeign.createOrUpdatePropurchase(request);
    }

    @ApiOperation(value = "创建采购意向表")
    @PostMapping("/createPropurchase")
    public BaseResponse createPropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pc createPropurchase");
        return preFeign.createPropurchase(request);
    }

    @ApiOperation(value = "修改采购意向表")
    @PostMapping("/updatePropurchase")
    public BaseResponse updatePropurchase(@RequestBody ProPurchaseDto request) {
        log.info("pc updatePropurchase");
        return preFeign.updatePropurchase(request);
    }

    @ApiOperation(value = "根据id查询采购意向表")
    @GetMapping("/getPropurchaseById")
    public BaseResponse getPropurchaseById(@RequestParam("id") String id) {
        log.info("pc getPropurchaseById");
        return preFeign.getPropurchaseById(id);
    }

    @ApiOperation(value = "列表查询采购意向表")
    @PostMapping("/getPropurchaseList")
    public BaseResponse getPropurchaseList(@RequestBody ProPurchaseDto request) {
        log.info("pc getPropurchaseList");
        return preFeign.getPropurchaseList(request);
    }


}
