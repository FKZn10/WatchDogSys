package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.service.ISysRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "角色", value = "角色")
@RequestMapping("/v1/OrganizationManage")
public class SysRoleController {
    @Resource
    private ISysRoleService sysRoleService;


    @TenantId
    @PostMapping("/GetRoleList")
    public BaseResponse GetRoleList(@RequestBody RoleDto request) {
        log.info("GetRoleList");
        return sysRoleService.GetRoleList(request);
    }

    @TenantId
    @PostMapping("/CreateOrUpdateRole")
    public BaseResponse CreateOrUpdateRole(@RequestBody RoleDto request) {
        log.info("CreateOrUpdateRole");
        return sysRoleService.CreateOrUpdateRole(request);
    }

    @PostMapping("/DeleteRole")
    public BaseResponse DeleteRole(@RequestBody RoleDto request) {
        log.info("DeleteRole");
        return sysRoleService.DeleteRole(request);
    }

    @GetMapping("/GetRoleCheckBox")
    public BaseResponse GetRoleCheckBox(@RequestParam(required = false) String powerId
            , @RequestParam(required = false) String cId, @RequestHeader(required = false) String token) {
        log.info("GetRoleCheckBox");
        return sysRoleService.GetRoleCheckBox(powerId, cId,token);
    }
}
