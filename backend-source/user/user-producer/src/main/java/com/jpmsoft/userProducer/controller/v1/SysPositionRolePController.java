package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.pojo.dto.PositionRolePDto;
import com.jpmsoft.userProducer.service.ISysPositionRolePService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "项目岗位角色关系", value = "项目岗位角色关系")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionRolePController {
    @Resource
    private ISysPositionRolePService sysPositionRolePService;

    @PostMapping("/getRoleByPositionP")
    public BaseResponse getRoleByPositionP(@RequestBody @Validated PositionPDto request) {
        log.info("getRoleByPositionP");
        return sysPositionRolePService.getRoleByPositionP(request);
    }


    @PostMapping("/getPositionRolePByPId")
    public BaseResponse getPositionRolePByPId(@RequestBody PositionRolePDto request) {
        log.info("getPositionRolePByPId");
        return sysPositionRolePService.getPositionRolePByPId(request);
    }

}
