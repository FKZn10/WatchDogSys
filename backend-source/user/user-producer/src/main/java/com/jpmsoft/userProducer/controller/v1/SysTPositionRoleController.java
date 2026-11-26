package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.service.ISysTPositionRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "岗位模板角色", value = "岗位模板角色")
@RequestMapping("/v1/OrganizationManage")
public class SysTPositionRoleController {
    @Resource
    private ISysTPositionRoleService sysTPositionRoleService;

    /**
     * 根据项目岗位模板获取角色
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据项目岗位模板获取角色")
    @PostMapping("/getRoleByT_Position")
    public BaseResponse getRoleByT_Position(@RequestBody @Validated TPositionDto request) {
        log.info("getRoleByT_Position");
        return sysTPositionRoleService.getRoleByT_Position(request);
    }
}
