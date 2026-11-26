package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanChangeDto;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 16:01
 */
@Slf4j
@RestController
@RequestMapping("/v1/OA/annualPlanChange")
public class OaAnnualPlanChangeController {

    @Resource
    private IOaAnnualPlanChangeService oaAnnualPlanChangeService;


    @TenantId
    @PostMapping("/createOrUpdateOaAnnualPlanChange")
    public BaseResponse createOrUpdateOaAnnualPlanChange(@RequestBody OaAnnualPlanChangeDto request) {
        log.info("createOrUpdateOaAnnualPlanChange");
        return oaAnnualPlanChangeService.createOrUpdateOaAnnualPlanChange(request);
    }

    @TenantId
    @PostMapping("/getOaAnnualPlanChangeList")
    public BaseResponse getOaAnnualPlanChangeList(@RequestBody OaAnnualPlanChangeDto request) {
        log.info("getOaAnnualPlanChangeList");
        return oaAnnualPlanChangeService.getOaAnnualPlanChangeList(request);
    }

    @GetMapping("/getOaAnnualPlanChangeById")
    public BaseResponse getOaAnnualPlanChangeById(@RequestParam String id) {
        log.info("getOaAnnualPlanChangeById");
        return oaAnnualPlanChangeService.getOaAnnualPlanChangeById(id);
    }

    @GetMapping("/approval2SyncOaAnnualPlan")
    public BaseResponse approval2SyncOaAnnualPlan(@RequestParam String id) {
        log.info("approval2SyncOaAnnualPlan");
        return oaAnnualPlanChangeService.approval2SyncOaAnnualPlan(id);
    }

}
