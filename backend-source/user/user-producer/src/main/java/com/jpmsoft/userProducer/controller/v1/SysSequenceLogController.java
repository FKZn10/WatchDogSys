package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceLogDTO;
import com.jpmsoft.userProducer.service.ISysSequenceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 编码日志
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/15 9:48
 */
@Slf4j
@Api(tags = "编码日志", value = "编码日志")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysSequenceLogController {

    @Resource
    private ISysSequenceLogService sysSequenceLogService;

    @ApiOperation(value = "分页查询编码日志")
    @PostMapping("/getSequenceLogList")
    public BaseResponse getSequenceLogList(@RequestBody SysSequenceLogDTO request) {
        log.info("getSequenceLogList");
        return sysSequenceLogService.getSequenceLogList(request);
    }
}
