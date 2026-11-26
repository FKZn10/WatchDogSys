package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonGlobalDTO;
import com.jpmsoft.userProducer.service.ISysCommonGlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 全局默认功能菜单或外部系统入口
 */
@Slf4j
@Api(tags = "全局常用功能设置", value = "全局常用功能设置")
@RestController
@RequestMapping("/v1/sysCommonGlobal")
public class SysCommonGlobalController {
    @Resource
    private ISysCommonGlobalService sysCommonGlobalService;

    /**
     * 创建或更新全局常用功能设置。
     *
     * @param request 功能目标和展示设置
     * @return 保存后的配置
     */
    @ApiOperation(value = "创建或更新全局常用功能设置")
    @PostMapping("/createOrUpdateSysCommonGlobal")
    public BaseResponse createOrUpdateSysCommonGlobal(@RequestBody SysCommonGlobalDTO request) {
        log.info("createOrUpdateSysCommonGlobal");
        return sysCommonGlobalService.createOrUpdateSysCommonGlobal(request);
    }

    /**
     * 分页获取全局常用功能设置列表。
     *
     * @param request 分页参数及功能目标筛选条件
     * @return 当前页列表和总数
     */
    @ApiOperation(value = "分页获取全局常用功能设置列表")
    @PostMapping("/getSysCommonGlobalList")
    public BaseResponse getSysCommonGlobalList(@RequestBody SysCommonGlobalDTO request) {
        log.info("getSysCommonGlobalList");
        return sysCommonGlobalService.getSysCommonGlobalList(request);
    }

    /**
     * 获取全局常用功能设置详情。
     *
     * @param id 全局常用功能设置ID
     * @return 全局常用功能设置详情
     */
    @ApiOperation(value = "获取全局常用功能设置详情")
    @GetMapping("/getSysCommonGlobalById")
    public BaseResponse getSysCommonGlobalById(@RequestParam String id) {
        log.info("getSysCommonGlobalById");
        return sysCommonGlobalService.getSysCommonGlobalById(id);
    }

    /**
     * 删除全局常用功能设置。
     *
     * @param id 全局常用功能设置ID
     * @return 删除结果
     */
    @ApiOperation(value = "删除全局常用功能设置")
    @GetMapping("/deleteSysCommonGlobalById")
    public BaseResponse deleteSysCommonGlobalById(@RequestParam String id) {
        log.info("deleteSysCommonGlobalById");
        return sysCommonGlobalService.deleteSysCommonGlobalById(id);
    }

    /**
     * 切换启用禁用状态。
     *
     * @param id 全局常用功能设置ID
     * @return 切换结果
     */
    @ApiOperation(value = "切换启用禁用状态")
    @GetMapping("/switchSysCommonExternalById")
    public BaseResponse switchSysCommonExternalById(@RequestParam String id) {
        log.info("switchSysCommonExternalById");
        return sysCommonGlobalService.switchSysCommonExternalById(id);
    }

    /**
     * 交换两个全局常用功能在列表中的位置。
     *
     * @param id      第一个全局常用功能设置ID
     * @param otherId 第二个全局常用功能设置ID
     * @return 交换结果
     */
    @ApiOperation("调换顺序")
    @GetMapping("/changeSort")
    public BaseResponse changeSort(@RequestParam String id, @RequestParam String otherId) {
        log.info("changeSort");
        return sysCommonGlobalService.changeSort(id, otherId);
    }

    /**
     * 在同一置顶分组内上移或下移全局常用功能。
     *
     * @param id     当前全局配置ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @ApiOperation("上移或下移全局常用功能")
    @GetMapping("/upOrDownSysCommonGlobalSort")
    public BaseResponse upOrDownSysCommonGlobalSort(@RequestParam String id, @RequestParam String action) {
        return sysCommonGlobalService.upOrDownSysCommonGlobalSort(id, action);
    }
}
