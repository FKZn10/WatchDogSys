package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyMaintenanceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/zdyxy/maintenanceRecord")
public class ZdyxyMaintenanceRecordController {
    @Autowired
    private ZdyxyMaintenanceRecordService maintenanceRecordService;

    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody MaintenanceRecordModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordService.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody MaintenanceRecordModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordService.approvalData(request);
    }
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody MaintenanceRecordModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordService.getList(request, token);
    }

    @PostMapping("/getListAuth")
    public BaseResponse getListAuth(@RequestBody MaintenanceRecordModel request, @RequestHeader String token) {
        log.info("getListAuth:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordService.getListAuth(request, token);
    }

    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("mrId") String mrId) {
        log.info("getDetail");
        return maintenanceRecordService.getDetail(mrId);
    }

    @GetMapping("/syncApplicationWarrantyStatus")
    public BaseResponse syncApplicationWarrantyStatus(@RequestParam("id") String id) {
        log.info("syncApplicationWarrantyStatus");
        return maintenanceRecordService.syncApplicationWarrantyStatus(id);
    }

    /**
     * 定时任务
     * 根据状态更新维修记录状态
     */
    @GetMapping("/updateRepairRecordStatus")
    public BaseResponse updateRepairRecordStatus() {
        log.info("updateRepairRecordStatus");
        return maintenanceRecordService.updateRepairRecordStatus();
    }

}
