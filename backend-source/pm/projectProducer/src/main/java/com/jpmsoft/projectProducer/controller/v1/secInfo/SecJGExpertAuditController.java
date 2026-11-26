package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertAuditService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家入库 （建管）
 */
@Slf4j
@RestController
@RequestMapping("/v1/jgExpert/audit")
public class SecJGExpertAuditController {

    @Resource
    private ISecJGExpertAuditService secJGExpertAuditService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecJGExpertAudit")
    public BaseResponse createOrUpdateSecJGExpertAudit(@RequestBody SecJGExpertAuditDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecJGExpertAudit");
        return secJGExpertAuditService.createOrUpdateSecJGExpertAudit(request,redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/createOrUpdateSecJGExpertAuditDz")
    public BaseResponse createOrUpdateSecJGExpertAuditDz(@RequestBody SecJGExpertAuditDTO request,@RequestHeader String token) {
        log.info("createOrUpdateSecJGExpertAuditDz");
        return secJGExpertAuditService.createOrUpdateSecJGExpertAuditDz(request,redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecJGExpertAuditList")
    public BaseResponse getSecJGExpertAuditList(@RequestBody SecJGExpertAuditDTO request) {
        log.info("getSecJGExpertAuditList");
        return secJGExpertAuditService.getSecJGExpertAuditList(request);
    }

    @GetMapping("/getSecJGExpertAuditById")
    public BaseResponse getSecJGExpertAuditById(@RequestParam String id) {
        log.info("getSecJGExpertAuditById");
        return secJGExpertAuditService.getSecJGExpertAuditById(id);
    }

    @GetMapping("/approveSyncExpertState")
    public BaseResponse approveSyncExpertState(@RequestParam String id) {
        log.info("approveSyncExpertState");
        return secJGExpertAuditService.approveSyncExpertState(id);
    }

}
