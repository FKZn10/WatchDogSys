package com.jpmsoft.projectConsumer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingItemRequest;
import com.jpmsoft.projectConsumer.feign.ActualMeasurementFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "图纸管理", value = "图纸管理")
@RequestMapping("/v1/drawingItem")
public class drawingItemController {

    @Resource
    private ActualMeasurementFeign actualMeasurementFeign;

    @ApiOperation(value = "新增或修改图纸")
    @PostMapping("/createOrUpdateDrawingItem")
    public BaseResponse createOrUpdateDrawingItem(@RequestBody DrawingItemRequest request, @RequestHeader String token){
        log.info("createOrUpdateDrawingItem");
        return actualMeasurementFeign.createOrUpdateDrawingItem(request,token);
    }

    @ApiOperation(value = "查询图纸列表")
    @PostMapping("/getDrawingItemList")
    public BaseResponse getDrawingItemList(@RequestBody DrawingItemRequest request, @RequestHeader String token){
        log.info("getDrawingItemList");
        return actualMeasurementFeign.getDrawingItemList(request,token);
    }

    @ApiOperation(value = "查询图纸详情")
    @GetMapping("/getDrawingItemById")
    public BaseResponse getDrawingItemById(@RequestParam String id){
        log.info("getDrawingItemById");
        return actualMeasurementFeign.getDrawingItemById(id);
    }


    @GetMapping("/getDrawingItemListByDmId")
    public BaseResponse getDrawingItemListByDmId(@RequestParam String dmId){
        log.info("getDrawingItemListByDmId");
        return actualMeasurementFeign.getDrawingItemListByDmId(dmId);
    }

}
