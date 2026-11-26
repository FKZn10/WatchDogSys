package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDTO;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:56
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/audit")
public class SecExpertAuditController {

    @Resource
    private ISecExpertAuditService secExpertAuditService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecExpertAudit")
    public BaseResponse createOrUpdateSecExpertAudit(@RequestBody SecExpertAuditDTO request,@RequestHeader String token) {
        log.info("createOrUpdateSecExpertAudit");
        return secExpertAuditService.createOrUpdateSecExpertAudit(request,redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/createOrUpdateSecExpertAuditDz")
    public BaseResponse createOrUpdateSecExpertAuditDz(@RequestBody SecExpertAuditDTO request,@RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditDz");
        return secExpertAuditService.createOrUpdateSecExpertAuditDz(request,redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecExpertAuditList")
    public BaseResponse getSecExpertAuditList(@RequestBody SecExpertAuditDTO request) {
        log.info("getSecExpertAuditList");
        return secExpertAuditService.getSecExpertAuditList(request);
    }

    @GetMapping("/getSecExpertAuditById")
    public BaseResponse getSecExpertAuditById(@RequestParam String id) {
        log.info("getSecExpertAuditById");
        return secExpertAuditService.getSecExpertAuditById(id);
    }

    @GetMapping("/approve2SyncExpertState")
    public BaseResponse approve2SyncExpertState(@RequestParam String id) {
        log.info("approve2SyncExpertState");
        return secExpertAuditService.approve2SyncExpertState(id);
    }

    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secExpertAuditService.returnInitiatorToTask(request,token);
    }

    @GetMapping("/getSecExpertAuditDetailListGroup")
    public BaseResponse getSecExpertAuditDetailListGroup() {
        log.info("getSecExpertAuditDetailListGroup");
        return secExpertAuditService.getSecExpertAuditDetailListGroup();
    }

    @GetMapping("/getSecExpertAuditByYear")
    public BaseResponse getSecExpertAuditByYear(@RequestParam Integer year) {
        log.info("getSecExpertAuditByYear");
        return secExpertAuditService.getSecExpertAuditByYear(year);
    }

    @GetMapping("/invalidExpertProcessData")
    public BaseResponse invalidExpertProcessData(@RequestParam String id) {
        log.info("invalidExpertProcessData");
        return secExpertAuditService.invalidExpertProcessData(id);
    }

    @GetMapping("/getSecExpertAuditChangeStatusInfoByYear")
    public BaseResponse getSecExpertAuditChangeStatusInfoByYear(@RequestParam String exApplicantCId, @RequestParam(required = false) Integer year) {
        log.info("getSecExpertAuditChangeStatusInfoByYear");
        return secExpertAuditService.getSecExpertAuditChangeStatusInfoByYear(exApplicantCId, year);
    }
}
