package com.jpmsoft.projectProducer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.EquipmentManagementRequest;
import com.jpmsoft.projectProducer.service.actualMeasurement.IEquipmentManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/equipmentManagement")
public class EquipmentManagementController {

    @Autowired
    private IEquipmentManagementService equipmentManagementService;

    @PostMapping("/getEquipmentManagementList")
    public BaseResponse getEquipmentManagementList(@RequestBody EquipmentManagementRequest request, @RequestHeader String token){
        log.info("getEquipmentManagementList");
        return equipmentManagementService.getEquipmentManagementList(request,token);
    }



}
