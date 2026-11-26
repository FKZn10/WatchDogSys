package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.PowerDto;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysPowerService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "权限", value = "权限")
@RequestMapping("/v1/SystemManage")
public class SysPowerController {

    @Resource
    private ISysPowerService sysPowerService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getPowerTreeList")
    public BaseResponse getPowerTreeList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "powerCode", required = false) String powerCode,
            @RequestHeader String token
    ) {
        log.info("getPowerTreeList");
        return sysPowerService.getPowerTreeList(title, powerCode, redisUtils.getTeId(token));
    }

    /**
     * CUD操作
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/CUDPower")
    public BaseResponse CUDPower(@RequestBody PowerDto request) {
        log.info("getPowerTreeList");
        return sysPowerService.CUDPower(request);
    }

    /**
     * 获取资源菜单树型结构
     *
     * @return
     */
    @GetMapping("/getPowerTree")
    public BaseResponse getPowerTree(@RequestHeader String token) {
        log.info("getPowerTree");
        LogUserInfoVo data = redisUtils.getUserInfoByToken(token);
        return sysPowerService.getPowerTree(!data.getCode().equalsIgnoreCase("admin") && !data.getCode().equalsIgnoreCase("jpmadmin"), data.getTeId());
    }

    /**
     * 上下移动排序
     *
     * @param powerId
     * @param action
     * @return
     */
    @ApiOperation(value = "权限上下移动排序")
    @GetMapping("/upOrDownPowerSort")
    public BaseResponse upOrDownPowerSort(@RequestParam String powerId, @RequestParam String action, @RequestHeader String token) {
        log.info("upOrDownPowerSort");
        return sysPowerService.upOrDownPowerSort(powerId, action, redisUtils.getTeId(token));
    }

    /**
     * 获取系统类型的菜单
     *
     * @return
     */
    @ApiOperation(value = "获取系统类型的菜单")
    @GetMapping("/getSystemPower")
    public BaseResponse getSystemPower(@RequestHeader String token) {
        log.info("getSystemPower");
        return sysPowerService.getSystemPower(redisUtils.getTeId(token));
    }

    @ApiOperation(value = "根据权限ID+上级权限编码获取其按钮权限（同时标记是否勾选）")
    @GetMapping("/getPowerCheckByRoleAndCode")
    public BaseResponse getPowerCheckByRoleAndCode(@RequestParam String roleId, @RequestParam(required = false) String code) {
        log.info("getPowerCheckByRoleAndCode");
        return sysPowerService.getPowerCheckByRoleAndCode(roleId, code);
    }

    @ApiIgnore
    @ApiOperation(value = "根据上级编码删除所有下级")
    @GetMapping("/deleteByParentCode.do")
    public BaseResponse deleteByParentCode(@RequestParam String code) {
        log.info("deleteByParentCode");
        return sysPowerService.deleteByParentCode(code);
    }

    @ApiOperation(value = "批量保存菜单资源")
    @PostMapping("/BatchSavePower")
    public BaseResponse BatchSavePower(@RequestBody List<PowerDto> powerDtoList, @RequestHeader String token) {
        log.info("BatchSavePower");
        String teId = redisUtils.getTeId(token);
        return sysPowerService.BatchSavePower (powerDtoList,teId);
    }


}
