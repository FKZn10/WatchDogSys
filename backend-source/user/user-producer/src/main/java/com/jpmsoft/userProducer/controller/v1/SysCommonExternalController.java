package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonExternalDTO;
import com.jpmsoft.userProducer.service.ISysCommonExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 常用功能外部系统入口
 */
@Slf4j
@Api(tags = "常用功能外部系统入口", value = "常用功能外部系统入口")
@RestController
@RequestMapping("/v1/sysCommonExternal")
public class SysCommonExternalController {
    @Resource
    private ISysCommonExternalService sysCommonExternalService;

    /**
     * 按父级关系获取外部系统入口树。
     *
     * @param request 可选筛选条件
     * @return 层级入口节点
     */
    @PostMapping("/getSysCommonExternalTree")
    @ApiOperation(value = "获取常用功能外部系统入口树", notes = "获取常用功能外部系统入口树")
    public BaseResponse getSysCommonExternalTree(@RequestBody SysCommonExternalDTO request) {
        log.info("getSysCommonExternalTree");
        return sysCommonExternalService.getSysCommonExternalTree(request);
    }

    /**
     * 创建或更新常用功能外部系统入口。
     *
     * @param request 入口信息
     * @return 保存后的入口
     */
    @PostMapping("/createOrUpdateSysCommonExternal")
    @ApiOperation(value = "创建或更新常用功能外部系统入口", notes = "创建或更新常用功能外部系统入口")
    public BaseResponse createOrUpdateSysCommonExternal(@RequestBody SysCommonExternalDTO request) {
        log.info("createOrUpdateSysCommonExternal");
        return sysCommonExternalService.createOrUpdateSysCommonExternal(request);
    }

    /**
     * 按主键获取外部系统入口。
     *
     * @param id 入口ID
     * @return 入口详情
     */
    @GetMapping("/getSysCommonExternalById")
    @ApiOperation(value = "获取常用功能外部系统入口", notes = "获取常用功能外部系统入口")
    public BaseResponse getSysCommonExternalById(@RequestParam("id") String id) {
        log.info("getSysCommonExternalById");
        return sysCommonExternalService.getSysCommonExternalById(id);
    }

    /**
     * 删除没有子节点和常用功能引用的外部入口。
     *
     * @param id 入口ID
     * @return 删除结果
     */
    @GetMapping("/deleteSysCommonExternalById")
    @ApiOperation(value = "删除常用功能外部系统入口", notes = "删除常用功能外部系统入口")
    public BaseResponse deleteSysCommonExternalById(@RequestParam("id") String id) {
        log.info("deleteSysCommonExternalById");
        return sysCommonExternalService.deleteSysCommonExternalById(id);
    }

    /**
     * 切换外部入口的启用状态。
     *
     * @param id 入口ID
     * @return 更新后的入口
     */
    @GetMapping("switchSysCommonExternalById")
    @ApiOperation("切换启用禁用状态")
    public BaseResponse switchSysCommonExternalById(@RequestParam("id") String id) {
        log.info("switchSysCommonExternalById");
        return sysCommonExternalService.switchSysCommonExternalById(id);
    }

    /**
     * 在同级入口中上移或下移指定节点。
     *
     * @param id     当前入口ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @GetMapping("/upOrDownSysCommonExternalSort")
    @ApiOperation("外部系统入口上移下移排序")
    public BaseResponse upOrDownSysCommonExternalSort(@RequestParam("id") String id,
                                                      @RequestParam("action") String action) {
        log.info("upOrDownSysCommonExternalSort");
        return sysCommonExternalService.upOrDownSysCommonExternalSort(id, action);
    }
}
