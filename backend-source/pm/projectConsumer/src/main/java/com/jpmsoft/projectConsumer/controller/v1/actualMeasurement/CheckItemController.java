package com.jpmsoft.projectConsumer.controller.v1.actualMeasurement;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.projectConsumer.feign.ActualMeasurementFeign;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import com.jpmsoft.projectConsumer.pojo.dto.common.OnDragDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "检查项", value = "检查项")
@RequestMapping("/v1/CheckItem")
public class CheckItemController {
    @Resource
    private ActualMeasurementFeign actualMeasurementFeign;

    @ApiOperation(value = "新增或修改检查项")
    @PostMapping("/createOrUpdateCheckItem")
    public BaseResponse createOrUpdateCheckItem(@RequestBody CheckItemRequest request, @RequestHeader String token) {
        log.info("createOrUpdateCheckItem");
        return actualMeasurementFeign.createOrUpdateCheckItem(request,token);
    }


    @ApiOperation(value = "删除检查项")
    @GetMapping("/deleteCheckItem")
    public BaseResponse deleteCheckItem(@RequestParam String id) {
        log.info("deleteCheckItem");
        return actualMeasurementFeign.deleteCheckItem(id);
    }

    @ApiOperation(value = "查询检查项详情")
    @GetMapping("/getCheckItemById")
    public BaseResponse getCheckItemById(@RequestParam String id) {
        log.info("getCheckItemById");
        return actualMeasurementFeign.getCheckItemById(id);
    }

    @ApiOperation(value = "查询检查项树")
    @GetMapping("/getCheckItemTree")
    public BaseResponse getCheckItemTree(@RequestHeader String token) {
        log.info("getCheckItemTree");
        return actualMeasurementFeign.getCheckItemTree(token);
    }

    @ApiOperation(value = "上移或下移检查项")
    @GetMapping("/upOrDownCheckItem")
    public BaseResponse upOrDownCheckItem(@RequestParam String ciId, @RequestParam String action) {
        log.info("upOrDownCheckItem");
        return actualMeasurementFeign.upOrDownCheckItem(ciId, action);
    }

    @ApiOperation(value = "查询所有数据")
    @GetMapping("/selectAll")
    public BaseResponse selectAll(@RequestHeader String token){
        log.info("selectAll");
        return actualMeasurementFeign.selectAll(token);
    }

    @ApiOperation(value = "检查项拖拽")
    @PostMapping("/onDragCheckItem")
    public BaseResponse onDragCheckItem(@RequestBody OnDragDto request) {
        log.info("onDragCheckItem");
        return actualMeasurementFeign.onDragCheckItem(request);
    }

    @ApiOperation(value = "检查项列表")
    @PostMapping("/getCheckItemList")
    public BaseResponse getCheckItemList(@RequestBody CheckItemRequest request) {
        log.info("getCheckItemList");
        return actualMeasurementFeign.getCheckItemList(request);
    }
}
