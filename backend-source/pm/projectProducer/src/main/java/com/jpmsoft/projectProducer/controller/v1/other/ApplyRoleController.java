package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.other.IApplyRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/applyRole")
public class ApplyRoleController {

    @Resource
    private IApplyRoleService applyRoleService;

    @GetMapping(value = "/approve2SyncDepartmentP")
    public BaseResponse approve2SyncDepartmentP(@RequestParam String id, @RequestHeader String token) {
        return applyRoleService.approve2SyncDepartmentP(id, token);
    }

    @GetMapping(value = "/syncAll")
    public BaseResponse syncAll(@RequestHeader String token) {
        return applyRoleService.syncAll(token);
    }

}
