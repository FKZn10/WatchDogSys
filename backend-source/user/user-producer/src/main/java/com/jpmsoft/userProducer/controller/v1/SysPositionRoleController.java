package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.pojo.dto.UserPDPDto;
import com.jpmsoft.userProducer.service.ISysPositionRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "岗位角色", value = "岗位角色")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionRoleController {
    @Resource
    private ISysPositionRoleService sysPositionRoleService;

    @PostMapping("/getRoleByPosition")
    public BaseResponse getRoleByPosition(@RequestBody PositionDto request) {
        log.info("getRoleByPosition");
        return sysPositionRoleService.getRoleByPosition(request);
    }

    @GetMapping("/getUserRole")
    public BaseResponse getUserRole(@RequestParam String uId) {
        return BaseResponse.ok(sysPositionRoleService.loginToGetUserRoleName(uId,null));
    }

    /**
     * 获取用户角色的来源
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取用户角色的来源")
    @PostMapping("/getUserRoleSource")
    public BaseResponse getUserRoleSource(@RequestBody UserPDPDto request, @RequestHeader String token) {
        log.info("getUserRoleSource");
        return sysPositionRoleService.getUserRoleSource(request, token);
    }
}
