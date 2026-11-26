package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SysConfigBaseDTO;
import com.jpmsoft.userProducer.service.ISysConfigBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 配置信息
 */
@Slf4j
@RestController
@Api(tags = "配置信息（新）", value = "配置信息（新）")
@RequestMapping("/v1/systemConfig")
public class SysConfigBaseController {
    @Resource
    private ISysConfigBaseService sysConfigBaseService;

    /**
     * 新增或更新配置信息
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @ApiOperation(value = "新增或更新配置信息")
    @PostMapping("/createOrUpdateSysConfigBase")
    public BaseResponse createOrUpdateSysConfigBase(@RequestBody SysConfigBaseDTO request) {
        log.info("createOrUpdateSysConfigBase");
        return sysConfigBaseService.createOrUpdateSysConfigBase(request);
    }

    /**
     * 获取配置信息列表
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @ApiOperation(value = "获取配置信息列表")
    @PostMapping("/getSysConfigBaseList")
    public BaseResponse getSysConfigBaseList(@RequestBody SysConfigBaseDTO request) {
        log.info("getSysConfigBaseList");
        return sysConfigBaseService.getSysConfigBaseList(request);
    }

    /**
     * 根据ID获取配置信息
     *
     * @param id 配置信息ID
     * @return 响应结果
     */
    @ApiOperation(value = "根据ID获取配置信息")
    @GetMapping("/getSysConfigBaseById")
    public BaseResponse getSysConfigBaseById(@RequestParam String id) {
        log.info("getSysConfigBaseById");
        return sysConfigBaseService.getSysConfigBaseById(id);
    }

    /**
     * 根据codes获取配置信息
     *
     * @param codes 配置信息codes
     * @return 响应结果
     */
    @ApiOperation(value = "根据codes获取配置信息")
    @GetMapping("/getSysConfigBaseByCodes")
    public BaseResponse getSysConfigBaseByCodes(@RequestParam String codes) {
        log.info("getSysConfigBaseByCodes");
        return sysConfigBaseService.getSysConfigBaseByCodes(codes);
    }

    /**
     * 根据ID删除配置信息
     *
     * @param id 配置信息ID
     * @return 响应结果
     */
    @ApiOperation(value = "根据ID删除配置信息")
    @GetMapping("/deleteSysConfigBaseById")
    public BaseResponse deleteSysConfigBaseById(@RequestParam String id) {
        log.info("deleteSysConfigBaseById");
        return sysConfigBaseService.deleteSysConfigBaseById(id);
    }
}
