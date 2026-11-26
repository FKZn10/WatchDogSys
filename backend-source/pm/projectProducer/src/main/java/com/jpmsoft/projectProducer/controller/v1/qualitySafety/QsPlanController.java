package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/qsPlan")
public class QsPlanController {
    @Resource
    private IQsPlanService qsPlanService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateQsPlan")
    public BaseResponse createOrUpdateQsPlan(@RequestBody QsPlanModel request) {
        log.info("createOrUpdateQsPlan");
        return qsPlanService.createOrUpdateQsPlan(request);
    }


    @TenantId
    @PostMapping("/getQsPlanList")
    public BaseResponse getQsPlanList(@RequestBody QsPlanModel request, @RequestHeader(value = "token") String token) {
        log.info("getQsPlanList");
        String userId = redisUtils.getUid(token);
        return qsPlanService.getQsPlanList(request, userId);
    }


    @GetMapping("/getQsPlanById")
    public BaseResponse getQsPlanById(@RequestParam String qspId) {
        log.info("getQsPlanById");
        return qsPlanService.getQsPlanById(qspId);
    }

    @PostMapping("/setQsPlanReplyState")
    public BaseResponse setQsPlanReplyState(@RequestBody QsPlanModel request) {
        log.info("setQsPlanReplyState");
        return qsPlanService.setQsPlanReplyState(request);
    }
}
