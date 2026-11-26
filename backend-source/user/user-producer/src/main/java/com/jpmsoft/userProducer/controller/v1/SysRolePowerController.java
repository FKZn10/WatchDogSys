package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.service.ISysRolePowerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "角色权限", value = "角色权限")
@RequestMapping("/v1/OrganizationManage")
public class SysRolePowerController {

    @Resource
    private ISysRolePowerService sysRolePowerService;

    @TenantId
    @PostMapping("/getPowerByRole")
    public BaseResponse getPowerByRole(@RequestBody @Validated RoleDto request) {
        log.info("getPowerByRole");
        return sysRolePowerService.getPowerByRole(request);
    }
}
