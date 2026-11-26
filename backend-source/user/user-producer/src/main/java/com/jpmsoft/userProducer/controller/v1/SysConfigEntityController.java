package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.ConfigDto;
import com.jpmsoft.userProducer.pojo.dto.ExecuteConfigDto;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "配置信息", value = "配置信息")
@RequestMapping("/v1/SystemManage")
public class SysConfigEntityController {

    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    /**
     * 新增or修改配置项信息
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增or修改配置项信息")
    @PostMapping("/CreateOrUpdateConfig")
    public BaseResponse CreateOrUpdateConfig(@RequestBody ConfigDto request, @RequestHeader String token) {
        log.info("CreateOrUpdateConfig");
        return sysConfigEntityService.CreateOrUpdateConfig(request, token);
    }

    /**
     * 删除配置项信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除配置项信息")
    @PostMapping("/DeleteConfig")
    public BaseResponse DeleteConfig(@RequestBody @Validated ConfigDto request) {
        log.info("DeleteConfig");
        return sysConfigEntityService.DeleteConfig(request);
    }

    /**
     * 配置列表
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "配置列表")
    @PostMapping("/GetConfigList")
    public BaseResponse GetConfigList(@RequestBody ConfigDto request) {
        log.info("GetConfigList");
        return sysConfigEntityService.GetConfigList(request);
    }

    @ApiOperation(value = "code获取配置信息")
    @GetMapping("/GetConfigValueByCode")
    public BaseResponse GetConfigValueByCode(@RequestParam String code, @RequestHeader(required = false) String token) {
        log.info("GetConfigValueByCode");
        return sysConfigEntityService.GetConfigValueByCode(code, token);
    }

    @ApiOperation(value = "批量code获取配置信息")
    @GetMapping("/GetConfigValuesByCodes")
    public BaseResponse GetConfigValuesByCodes(@RequestParam String codes, @RequestHeader(required = false) String token) {
        //log.info("GetConfigValuesByCodes");
        String[] allCode = codes.trim().split(",");
        List<String> data = Arrays.asList(allCode);
        return sysConfigEntityService.GetConfigValuesByCodes(data, token);
    }

    @ApiOperation(value = "code获取配置信息(附带conType等信息)")
    @GetMapping("/selectConfigByCode")
    public BaseResponse selectConfigByCode(@RequestParam String code, @RequestHeader(required = false) String token) {
        //log.info("selectConfigByCode");
        return sysConfigEntityService.selectByCode(code, token);
    }

    @ApiOperation(value = "执行config内配置的SQL（返回布尔值）")
    @PostMapping("/executeConfigAction")
    public BaseResponse executeConfigAction(@RequestBody ExecuteConfigDto request, @RequestHeader String token) {
        return sysConfigEntityService.executeConfigAction(request, token);
    }
}
