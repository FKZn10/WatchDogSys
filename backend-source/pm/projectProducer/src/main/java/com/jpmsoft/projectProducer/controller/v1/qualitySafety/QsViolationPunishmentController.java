package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsViolationPunishmentRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsViolationPunishmentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/qsViolation")
public class QsViolationPunishmentController {

    @Resource
    private IQsViolationPunishmentService qsViolationPunishmentService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateQsViolationPunishment")
    public BaseResponse createOrUpdateQsViolationPunishment(@RequestBody QsViolationPunishmentRequest request) {
        log.info("createOrUpdateQsViolationPunishment");
        return qsViolationPunishmentService.createOrUpdateQsViolationPunishment(request);
    }

    @TenantId
    @PostMapping("/getQsViolationPunishmentList")
    public BaseResponse getQsViolationPunishmentList(@RequestBody QsViolationPunishmentRequest request, @RequestHeader String token) {
        log.info("getQsViolationPunishmentList");
        return qsViolationPunishmentService.getQsViolationPunishmentList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getQsViolationPunishmentById")
    public BaseResponse getQsViolationPunishmentById(@RequestParam String vpId) {
        log.info("getQsViolationPunishmentById");
        return qsViolationPunishmentService.getQsViolationPunishmentById(vpId);
    }
}
