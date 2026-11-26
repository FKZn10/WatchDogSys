package com.jpmsoft.projectConsumer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.MaintenanceRecordFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "维保管理-维保记录", value = "维保管理-维保记录")
@RequestMapping("/v1/zdyxy/maintenanceRecord")
public class ZdyxyMaintenanceRecordController {
    @Autowired
    private MaintenanceRecordFeign maintenanceRecordFeign;

    @ApiOperation(value = "新增或修改维保记录")
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody MaintenanceRecordModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordFeign.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody MaintenanceRecordModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordFeign.approvalData(request);
    }
    @ApiOperation(value = "分页查询维保记录")
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody MaintenanceRecordModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordFeign.getList(request, token);
    }

    @ApiOperation(value = "分页查询维保记录（鉴权）")
    @PostMapping("/getListAuth")
    public BaseResponse getListAuth(@RequestBody MaintenanceRecordModel request, @RequestHeader String token) {
        log.info("getListAuth:{}", JacksonUtils.toJSONString(request));
        return maintenanceRecordFeign.getListAuth(request, token);
    }

    @ApiOperation(value = "ID查询维保记录")
    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("mrId") String mrId) {
        log.info("getDetail");
        return maintenanceRecordFeign.getDetail(mrId);
    }

    @ApiOperation("同步质保金申请状态到修缮项目申报")
    @GetMapping("/syncApplicationWarrantyStatus")
    public BaseResponse syncApplicationWarrantyStatus(@RequestParam("id") String id) {
        log.info("syncApplicationWarrantyStatus");
        return maintenanceRecordFeign.syncApplicationWarrantyStatus(id);
    }

    @GetMapping("/updateRepairRecordStatus")
    public BaseResponse updateRepairRecordStatus() {
        log.info("updateRepairRecordStatus");
        return maintenanceRecordFeign.updateRepairRecordStatus();
    }
}
