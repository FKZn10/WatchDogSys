package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "maintenanceRecord")
public interface MaintenanceRecordFeign {
    @PostMapping("/v1/zdyxy/maintenanceRecord/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody MaintenanceRecordModel request);
    @PostMapping("/v1/zdyxy/maintenanceRecord/approvalData")
    BaseResponse approvalData(@RequestBody MaintenanceRecordModel request);
    @PostMapping("/v1/zdyxy/maintenanceRecord/getList")
    BaseResponse getList(@RequestBody MaintenanceRecordModel request,@RequestHeader String token);

    @PostMapping("/v1/zdyxy/maintenanceRecord/getListAuth")
    BaseResponse getListAuth(@RequestBody MaintenanceRecordModel request,@RequestHeader String token);

    @GetMapping("/v1/zdyxy/maintenanceRecord/getDetail")
    BaseResponse getDetail(@RequestParam("mrId")String mrId);

    @GetMapping("/v1/zdyxy/maintenanceRecord/syncApplicationWarrantyStatus")
    BaseResponse syncApplicationWarrantyStatus(@RequestParam("id") String id);

    @GetMapping("/v1/zdyxy/maintenanceRecord/updateRepairRecordStatus")
    BaseResponse updateRepairRecordStatus();


}
