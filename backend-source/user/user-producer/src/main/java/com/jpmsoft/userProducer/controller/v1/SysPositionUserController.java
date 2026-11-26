package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionUserDto;
import com.jpmsoft.userProducer.pojo.dto.SysPositionUserDto;
import com.jpmsoft.userProducer.service.ISysPositionUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "用户岗位", value = "用户岗位")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionUserController {
    @Resource
    private ISysPositionUserService sysPositionUserService;

    @PostMapping("/CreateOrDeletePU")
    public BaseResponse CreateOrDeletePU(@RequestBody @Validated SysPositionUserDto request) {
        log.info("CreateOrDeletePU");
        return sysPositionUserService.CreateOrDeletePU(request);
    }

    @PostMapping("/updatePositionUser")
    public BaseResponse updatePositionUser(@RequestBody SysPositionUserDto request) {
        log.info("updatePositionUser");
        return sysPositionUserService.updatePositionUser(request);
    }

    @ApiOperation("上移下移排序")
    @GetMapping("/updatePuSortUpOrDown")
    public BaseResponse updateSortUpOrDown(@RequestParam String puId, @RequestParam String dId, @RequestParam String action) {
        log.info("updateSortUpOrDown");
        return sysPositionUserService.updateSortUpOrDown(puId, dId, action);
    }

    @PostMapping("/GetUserPositionList")
    public BaseResponse GetUserPositionList(@RequestBody PositionUserDto request) {
        log.info("GetUserPositionList");
        return sysPositionUserService.GetUserPositionList(request);
    }


    /**
     * 设置主部门
     *
     * @param puId   用户岗位关系ID
     * @param enable 是否为主部门
     * @return
     */
    @ApiOperation("设置主部门")
    @GetMapping("/setPuMainDep")
    public BaseResponse setPuMainDep(@RequestParam String puId, @RequestParam Boolean enable) {
        log.info("setMainDep");
        return sysPositionUserService.setMainDep(puId, enable);
    }

    /**
     * 设置主负责人
     *
     * @param puId   用户岗位关系ID
     * @param dId    部门ID
     * @param enable 是否为主部门
     * @return
     */
    @ApiOperation("设置主负责人")
    @GetMapping("/setPuMainDuty")
    public BaseResponse setPuMainDuty(@RequestParam String puId, @RequestParam String dId, @RequestParam Boolean enable) {
        log.info("setMainDuty");
        return sysPositionUserService.setMainDuty(puId, dId, enable);
    }

    /**
     * 设置为负责人
     *
     * @param puId
     * @param enable
     * @return
     */
    @ApiOperation("设置为负责人")
    @GetMapping("/setPuDuty")
    public BaseResponse setPuDuty(@RequestParam String puId, @RequestParam Boolean enable) {
        log.info("setDuty");
        return sysPositionUserService.setDuty(puId, enable);
    }
}
