package com.jpmsoft.projectConsumer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceWarrantyRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "维保管理-质量保修书", value = "维保管理-质量保修书")
@RequestMapping("/v1/maintenanceWarranty")
public class MaintenanceWarrantyController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改质量保修书")
    @PostMapping("/createOrUpdateMaintenanceWarranty")
    public BaseResponse createOrUpdateMaintenanceWarranty(@RequestBody MaintenanceWarrantyRequest request) {
        log.info("createOrUpdateMaintenanceWarranty");
        return baseFeign.createOrUpdateMaintenanceWarranty(request);
    }

    @ApiOperation(value = "分页查询质量保修书")
    @PostMapping("/getMaintenanceWarrantyList")
    public BaseResponse getMaintenanceWarrantyList(@RequestBody MaintenanceWarrantyRequest request, @RequestHeader String token) {
        log.info("getMaintenanceWarrantyList");
        return baseFeign.getMaintenanceWarrantyList(request, token);
    }

    @ApiOperation(value = "分页查询质量保修书-不鉴权")
    @PostMapping("/getMaintenanceWarrantyListNoAuth")
    public BaseResponse getMaintenanceWarrantyListNoAuth(@RequestBody MaintenanceWarrantyRequest request) {
        log.info("getMaintenanceWarrantyListNoAuth");
        return baseFeign.getMaintenanceWarrantyListNoAuth(request);
    }


    @ApiOperation(value = "ID查询质量保修书")
    @GetMapping("/getMaintenanceWarrantyById")
    public BaseResponse getMaintenanceWarrantyById(@RequestParam String mwId) {
        log.info("getMaintenanceWarrantyById");
        return baseFeign.getMaintenanceWarrantyById(mwId);
    }
}
