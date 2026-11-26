package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysParticipantUnitDto;
import com.jpmsoft.userProducer.service.ISysParticipantUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "项目参建单位", value = "项目参建单位")
@RequestMapping("/v1/participantUnit")
public class SysParticipantUnitController {

    @Resource
    private ISysParticipantUnitService sysParticipantUnitService;


    @ApiOperation(value = "创建项目参建单位")
    @PostMapping("/createSysParticipantUnit")
    public BaseResponse createSysParticipantUnit(@RequestBody @Validated SysParticipantUnitDto request) {
        log.info("createSysParticipantUnit");
        return sysParticipantUnitService.createSysParticipantUnit(request);
    }

    @ApiOperation(value = "分页查项目参建单位")
    @PostMapping("/getSysParticipantUnitList")
    public BaseResponse getSysParticipantUnitList(@RequestBody SysParticipantUnitDto request,@RequestHeader String token) {
        log.info("getSysParticipantUnitList");
        return sysParticipantUnitService.getSysParticipantUnitList(request,token);
    }

    @ApiOperation(value = "分页查项目参建单位(去重)")
    @PostMapping("/getSysParticipantUnitDistinctList")
    public BaseResponse getSysParticipantUnitDistinctList(@RequestBody SysParticipantUnitDto request,@RequestHeader String token) {
        log.info("getSysParticipantUnitDistinctList");
        return sysParticipantUnitService.getSysParticipantUnitDistinctList(request,token);
    }

    @ApiOperation(value = "ID删除项目参建单位")
    @GetMapping("/deleteSysParticipantUnitModelById")
    public BaseResponse deleteSysParticipantUnitModelById(@RequestParam String puId) {
        log.info("deleteSysParticipantUnitModelById");
        return sysParticipantUnitService.deleteSysParticipantUnitModelById(puId);
    }

    @ApiOperation(value = "项目ID查询参见单位(ctype每组创建时间最新的)")
    @GetMapping("/getSysParticipantUnitByPidGroupType")
    public BaseResponse getSysParticipantUnitByPidGroupType(@RequestParam String pId,@RequestHeader String token) {
        log.info("getSysParticipantUnitByPidGroupType");
        return sysParticipantUnitService.getSysParticipantUnitByPidGroupType(pId,token);
    }

}
