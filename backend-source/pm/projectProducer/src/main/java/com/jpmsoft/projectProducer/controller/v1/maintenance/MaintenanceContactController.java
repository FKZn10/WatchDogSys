package com.jpmsoft.projectProducer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceContactRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceContactService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/maintenanceContact")
public class MaintenanceContactController {

    @Resource
    private IMaintenanceContactService maintenanceContactService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateMaintenanceContact")
    public BaseResponse createOrUpdateMaintenanceContact(@RequestBody MaintenanceContactRequest request) {
        log.info("createOrUpdateMaintenanceContact");
        return maintenanceContactService.createOrUpdateMaintenanceContact(request);
    }

    @TenantId
    @PostMapping("/getMaintenanceContactList")
    public BaseResponse getMaintenanceContactList(@RequestBody MaintenanceContactRequest request, @RequestHeader String token) {
        log.info("getMaintenanceContactList");
        return maintenanceContactService.getMaintenanceContactList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMaintenanceContactById")
    public BaseResponse getMaintenanceContactById(@RequestParam String mcId) {
        log.info("getMaintenanceContactById");
        return maintenanceContactService.getMaintenanceContactById(mcId);
    }
}
