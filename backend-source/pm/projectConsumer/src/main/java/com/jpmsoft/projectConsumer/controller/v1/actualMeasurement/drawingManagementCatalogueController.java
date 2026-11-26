package com.jpmsoft.projectConsumer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.projectConsumer.feign.ActualMeasurementFeign;
import com.jpmsoft.projectConsumer.pojo.dto.common.OnDragDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "图纸目录", value = "图纸目录")
@RequestMapping("/v1/drawingManagementCatalogue")
public class drawingManagementCatalogueController {
    @Resource
    private ActualMeasurementFeign actualMeasurementFeign;

    @ApiOperation(value = "新增或修改图纸目录")
    @PostMapping("/createOrUpdateDrawingManagementCatalogue")
    public BaseResponse createOrUpdateDrawingManagementCatalogue(@RequestBody DrawingManagementCatalogueRequest request, @RequestHeader String token) {
        log.info("createOrUpdateDrawingManagementCatalogue");
        return actualMeasurementFeign.createOrUpdateDrawingManagementCatalogue(request,token);
    }

    @ApiOperation(value = "获取图纸目录树")
    @GetMapping("/getDrawingManagementCatalogueTree")
    public BaseResponse getDrawingManagementCatalogueTree(@RequestParam String pId,@RequestHeader String token) {
        log.info("getDrawingManagementCatalogueTree");
        return actualMeasurementFeign.getDrawingManagementCatalogueTree(pId,token);
    }

    @ApiOperation(value = "删除图纸目录")
    @GetMapping("/deleteDrawingManagementCatalogue")
    public BaseResponse deleteDrawingManagementCatalogue(@RequestParam String id) {
        log.info("deleteDrawingManagementCatalogue");
        return actualMeasurementFeign.deleteDrawingManagementCatalogueById(id);
    }

    @ApiOperation(value = "根据Id查询图纸目录")
    @GetMapping("/getDrawingManagementCatalogueById")
    public BaseResponse getDrawingManagementCatalogueById(@RequestParam String id) {
        log.info("getDrawingManagementCatalogueById");
        return actualMeasurementFeign.getDrawingManagementCatalogueById(id);
    }

    @PostMapping("/getDrawingManagementCatalogueTreeByUser")
    public BaseResponse getDrawingManagementCatalogueTreeByUser(@RequestBody DrawingManagementCatalogueRequest request) {
        log.info("getDrawingManagementCatalogueTreeByUser");
        return actualMeasurementFeign.getDrawingManagementCatalogueTreeByUser(request);
    }

    @ApiOperation(value = "上移或下移图纸目录")
    @GetMapping("/upOrDownDrawingManagementCatalogue")
    public BaseResponse upOrDownDrawingManagementCatalogue(@RequestParam String id, @RequestParam String action) {
        log.info("upOrDownDrawingManagementCatalogue");
        return actualMeasurementFeign.upOrDownDrawingManagementCatalogue(id, action);
    }

    @ApiOperation(value = "拖拽移动图纸目录")
    @PostMapping("/onDragDrawingManagementCatalogue")
    public BaseResponse onDragDrawingManagementCatalogue(@RequestBody OnDragDto request) {
        log.info("onDragDrawingManagementCatalogue");
        return actualMeasurementFeign.onDragDrawingManagementCatalogue(request);
    }

}
