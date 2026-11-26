package com.jpmsoft.projectProducer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceWarrantyRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceWarrantyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/maintenanceWarranty")
public class MaintenanceWarrantyController {

    @Resource
    private IMaintenanceWarrantyService maintenanceWarrantyService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateMaintenanceWarranty")
    public BaseResponse createOrUpdateMaintenanceWarranty(@RequestBody MaintenanceWarrantyRequest request) {
        log.info("createOrUpdateMaintenanceWarranty");
        return maintenanceWarrantyService.createOrUpdateMaintenanceWarranty(request);
    }

    @TenantId
    @PostMapping("/getMaintenanceWarrantyList")
    public BaseResponse getMaintenanceWarrantyList(@RequestBody MaintenanceWarrantyRequest request, @RequestHeader String token) {
        log.info("getMaintenanceWarrantyList");
        return maintenanceWarrantyService.getMaintenanceWarrantyList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getMaintenanceWarrantyListNoAuth")
    public BaseResponse getMaintenanceWarrantyListNoAuth(@RequestBody MaintenanceWarrantyRequest request) {
        log.info("getMaintenanceWarrantyListNoAuth");
        return maintenanceWarrantyService.getMaintenanceWarrantyListNoAuth(request);
    }


    @GetMapping("/getMaintenanceWarrantyById")
    public BaseResponse getMaintenanceWarrantyById(@RequestParam String mwId) {
        log.info("getMaintenanceWarrantyById");
        return maintenanceWarrantyService.getMaintenanceWarrantyById(mwId);
    }
}
