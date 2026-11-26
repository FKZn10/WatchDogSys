package com.jpmsoft.projectConsumer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceContactRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "维保管理-维保联系人", value = "维保管理-维保联系人")
@RequestMapping("/v1/maintenanceContact")
public class MaintenanceContactController {
    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改维保联系人")
    @PostMapping("/createOrUpdateMaintenanceContact")
    public BaseResponse createOrUpdateMaintenanceContact(@RequestBody MaintenanceContactRequest request) {
        log.info("createOrUpdateMaintenanceContact");
        return baseFeign.createOrUpdateMaintenanceContact(request);
    }

    @ApiOperation(value = "分页查询维保联系人")
    @PostMapping("/getMaintenanceContactList")
    public BaseResponse getMaintenanceContactList(@RequestBody MaintenanceContactRequest request, @RequestHeader String token) {
        log.info("getMaintenanceContactList");
        return baseFeign.getMaintenanceContactList(request, token);
    }

    @ApiOperation(value = "ID查询维保联系人")
    @GetMapping("/getMaintenanceContactById")
    public BaseResponse getMaintenanceContactById(@RequestParam String mcId) {
        log.info("getMaintenanceContactById");
        return baseFeign.getMaintenanceContactById(mcId);
    }
}
