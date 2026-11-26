package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageTempDto;
import com.jpmsoft.userProducer.service.ISysMessageTempService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息模板
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:15
 */
@Slf4j
@Api(tags = "消息模板", value = "消息模板")
@RestController
@RequestMapping("/msg/v1/messageTemp")
public class SysMessageTempController {

    @Resource
    private ISysMessageTempService sysMessageTempService;
    @Resource
    private RedisUtils redisUtils;

    @ApiOperation(value = "新增修改消息模板")
    @PostMapping("/createOrUpdateSysMessageTemp")
    public BaseResponse createOrUpdateSysMessageTemp(@RequestBody @Validated SysMessageTempDto request, @RequestHeader String token) {
        log.info("createOrUpdateSysMessageTemp");
        return sysMessageTempService.createOrUpdateSysMessageTemp(request, redisUtils.getUid(token));
    }

    @ApiOperation(value = "分页查询消息模板")
    @PostMapping("/getSysMessageTempList")
    public BaseResponse getSysMessageTempList(@RequestBody SysMessageTempDto request) {
        log.info("getSysMessageTempList");
        return sysMessageTempService.getSysMessageTempList(request);
    }

    @ApiOperation(value = "id查询消息模板")
    @GetMapping("/getSysMessageTempById")
    public BaseResponse getSysMessageTempById(@RequestParam String id) {
        log.info("getSysMessageTempById");
        return sysMessageTempService.getSysMessageTempById(id);
    }

    @ApiOperation(value = "id删除消息模板")
    @GetMapping("/delSysMessageTempById")
    public BaseResponse delSysMessageTempById(@RequestParam String id) {
        log.info("delSysMessageTempById");
        return sysMessageTempService.delSysMessageTempById(id);
    }


    @ApiOperation(value = "编码查询消息模板")
    @GetMapping("/getSysMessageTempByCode")
    public BaseResponse getSysMessageTempByCode(@RequestParam String code) {
        log.info("getSysMessageTempByCode");
        return BaseResponse.ok(sysMessageTempService.getSysMessageTempByCode(code));
    }
}
