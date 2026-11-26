package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.AuditLogSenderDTO;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.rabbitMQ.Sender;
import com.jpmsoft.userProducer.service.IWebAuditLogService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 22:34
 */
@Slf4j
@Api(tags = "web前端审计日志", value = "web前端审计日志")
@RestController
@RequestMapping("/v1/webAuditLog")
public class WebAuditLogController {

    @Resource
    private IWebAuditLogService webAuditLogService;
    @Resource
    private Sender sender;
    @Resource
    private RedisUtils redisUtils;


    @ApiOperation(value = "表单ID分页查询审计日志")
    @PostMapping("/getWebAuditLogList")
    public BaseResponse getWebAuditLogList(@RequestBody SysAuditLogQueryDto query) {
        log.info("getWebAuditLogList");
        return webAuditLogService.getWebAuditLogList(query);
    }

    @ApiOperation(value = "发送web审计日志到队列")
    @PostMapping("/sendWebAudit")
    public BaseResponse sendWebAudit(@RequestBody AuditLogSenderDTO request, @RequestHeader String token) {
        log.info("sendWebAudit");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        sender.sendWebAudit(request.getContent(), user.getId(), user.getName(), request.getId(), request.getAction());
        return BaseResponse.ok();
    }

    @ApiOperation(value = "批量发送web审计日志到队列")
    @PostMapping("/sendWebAuditBath")
    public BaseResponse sendWebAuditBath(@RequestBody List<AuditLogSenderDTO> request, @RequestHeader String token) {
        log.info("sendWebAuditBath");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        request.forEach(r -> {
            sender.sendWebAudit(r.getContent(), user.getId(), user.getName(), r.getId(), r.getAction());
        });
        return BaseResponse.ok();
    }
}
