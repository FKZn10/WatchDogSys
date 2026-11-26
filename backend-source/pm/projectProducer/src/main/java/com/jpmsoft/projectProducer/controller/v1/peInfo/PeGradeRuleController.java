package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeGradeRuleRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeGradeRuleService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peGradeRule")
public class PeGradeRuleController {
    @Resource
    private IPeGradeRuleService peGradeRuleService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeGradeRule")
    public BaseResponse createOrUpdatePeGradeRule(@RequestBody PeGradeRuleRequest request) {
        log.info("createOrUpdatePeGradeRule");
        return peGradeRuleService.createOrUpdatePeGradeRule(request);
    }

    @TenantId
    @PostMapping("/getPeGradeRuleList")
    public BaseResponse getPeGradeRuleList(@RequestBody PeGradeRuleRequest request, @RequestHeader String token) {
        log.info("getPeGradeRuleList");
        return peGradeRuleService.getPeGradeRuleList(request, redisUtils.getUid(token));
    }

    @GetMapping("/deletePeGradeRuleByRId")
    public BaseResponse deletePeGradeRuleByRId(@RequestParam String rId) {
        log.info("deletePeGradeRuleByRId");
        return peGradeRuleService.deletePeGradeRuleByRId(rId);
    }
}
