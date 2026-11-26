package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysVersionDto;
import com.jpmsoft.userProducer.service.ISysVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "系统版本更新记录", value = "系统版本更新记录")
@RequestMapping("/v1/sysVersion")
public class SysVersionController {

    @Resource
    private ISysVersionService sysVersionService;
    private static int index = 1;

    @PostMapping("/code")
    public BaseResponse code(@RequestParam boolean refreshNo, @RequestBody Object data) {
        int res = index;
        if (refreshNo) index += 1;
        return BaseResponse.ok(res);
    }

    @ApiOperation(value = "新增修改系统版本更新记录")
    @PostMapping("/createOrUpdateSysVersion")
    public BaseResponse createOrUpdateSysVersion(@RequestBody SysVersionDto request) {
        log.info("createOrUpdateSysVersion");
        return sysVersionService.createOrUpdateSysVersion(request);
    }

    @ApiOperation(value = "分页查询系统版本更新记录")
    @PostMapping("/getSysVersionModelList")
    public BaseResponse getSysVersionModelList(@RequestBody SysVersionDto request) {
        log.info("getSysVersionModelList");
        return sysVersionService.getSysVersionModelList(request);
    }

    @ApiOperation(value = "ID查询系统版本更新记录")
    @GetMapping("/getSysVersionModelById")
    public BaseResponse getSysVersionModelById(@RequestParam String vId) {
        log.info("getSysVersionModelById");
        return sysVersionService.getSysVersionModelById(vId);
    }

    @ApiOperation(value = "查询达梦到期时间")
    @GetMapping("/getDatabaseExpiredDate")
    public BaseResponse getDatabaseExpiredDate() {
        log.info("getDatabaseExpiredDate");
        return sysVersionService.getDatabaseExpiredDate();
    }
}
