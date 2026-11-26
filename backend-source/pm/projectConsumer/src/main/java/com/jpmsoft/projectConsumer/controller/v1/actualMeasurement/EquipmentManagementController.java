package com.jpmsoft.projectConsumer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.EquipmentManagementRequest;
import com.jpmsoft.projectConsumer.feign.ActualMeasurementFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "设备管理", value = "设备管理")
@RequestMapping("/v1/equipmentManagement")
public class EquipmentManagementController {

    @Resource
    private ActualMeasurementFeign actualMeasurementFeign;


    @ApiOperation(value = "分页查询查询设备列表")
    @PostMapping("/getEquipmentManagementList")
    public BaseResponse getEquipmentManagementList(@RequestBody EquipmentManagementRequest request, @RequestHeader String token){
        log.info("getEquipmentManagementList");
        return actualMeasurementFeign.getEquipmentManagementList(request,token);
    }



}
