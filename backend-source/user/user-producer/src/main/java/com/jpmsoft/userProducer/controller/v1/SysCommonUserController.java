package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysCommonUserDTO;
import com.jpmsoft.userProducer.service.ISysCommonUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户常用功能及个性化设置
 */
@Slf4j
@Api(tags = "用户常用功能设置", value = "用户常用功能设置")
@RestController
@RequestMapping("/v1/sysCommonUser")
public class SysCommonUserController {
    @Resource
    private ISysCommonUserService sysCommonUserService;

    /**
     * 添加或修改当前用户的常用功能。
     *
     * @param request 目标及个人设置
     * @return 保存后的个人设置
     */
    @ApiOperation("添加或修改个人常用功能")
    @PostMapping("/createOrUpdateSysCommonUser")
    public BaseResponse createOrUpdateSysCommonUser(@RequestBody SysCommonUserDTO request) {
        return sysCommonUserService.createOrUpdateSysCommonUser(request);
    }

    /**
     * 批量添加或修改当前用户的常用功能。
     *
     * @param requests 待添加或修改的功能及个人设置
     * @return 保存后的个人设置列表
     */
    @ApiOperation("批量添加或修改个人常用功能")
    @PostMapping("/batchCreateOrUpdateSysCommonUser")
    public BaseResponse batchCreateOrUpdateSysCommonUser(@RequestBody List<SysCommonUserDTO> requests) {
        return sysCommonUserService.batchCreateOrUpdateSysCommonUser(requests);
    }

    /**
     * 分页获取当前用户常用功能；尚未编辑时直接读取全局配置。
     *
     * @param request page、size及可选筛选条件
     * @return 当前页和筛选后的总数
     */
    @ApiOperation("获取个人常用功能列表")
    @PostMapping("/getSysCommonUserList")
    public BaseResponse getSysCommonUserList(@RequestBody SysCommonUserDTO request) {
        return sysCommonUserService.getSysCommonUserList(request);
    }

    /**
     * 按列表项ID获取当前用户的常用功能详情。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 当前用户可用的设置
     */
    @ApiOperation("获取个人常用功能详情")
    @GetMapping("/getSysCommonUserById")
    public BaseResponse getSysCommonUserById(@RequestParam String id) {
        return sysCommonUserService.getSysCommonUserById(id);
    }

    /**
     * 从当前用户的常用功能中移除指定项。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 移除结果
     */
    @ApiOperation("删除个人常用功能")
    @GetMapping("/deleteSysCommonUserById")
    public BaseResponse deleteSysCommonUserById(@RequestParam String id) {
        return sysCommonUserService.deleteSysCommonUserById(id);
    }

    /**
     * 切换当前用户对指定功能的显示或隐藏状态。
     *
     * @param id 个人设置ID或全局配置ID
     * @return 更新后的个人设置
     */
    @ApiOperation("切换个人常用功能显示状态")
    @GetMapping("/switchSysCommonUserById")
    public BaseResponse switchSysCommonUserById(@RequestParam String id) {
        return sysCommonUserService.switchSysCommonUserById(id);
    }

    /**
     * 交换当前用户列表中两个功能的位置。
     *
     * @param id      第一个列表项ID
     * @param otherId 第二个列表项ID
     * @return 交换结果
     */
    @ApiOperation("交换个人常用功能位置")
    @GetMapping("/changeSort")
    public BaseResponse changeSort(@RequestParam String id, @RequestParam String otherId) {
        return sysCommonUserService.changeSort(id, otherId);
    }

    /**
     * 在同一置顶分组内上移或下移当前用户的常用功能。
     *
     * @param id     当前列表项ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @ApiOperation("上移或下移个人常用功能")
    @GetMapping("/upOrDownSysCommonUserSort")
    public BaseResponse upOrDownSysCommonUserSort(@RequestParam String id, @RequestParam String action) {
        return sysCommonUserService.upOrDownSysCommonUserSort(id, action);
    }
}
