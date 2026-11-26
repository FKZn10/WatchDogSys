package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 16:01
 */
@Slf4j
@RestController
@RequestMapping("/v1/OA/annualPlan")
public class OaAnnualPlanController {

    @Resource
    private IOaAnnualPlanService oaAnnualPlanService;

    @Resource
    private IOaAnnualPlanDtlService oaAnnualPlanDtlService;

    @TenantId
    @PostMapping("/createOrUpdateOaAnnualPlan")
    public BaseResponse createOrUpdateOaAnnualPlan(@RequestBody OaAnnualPlanDto request) {
        log.info("createOrUpdateOaAnnualPlan");
        return oaAnnualPlanService.createOrUpdateOaAnnualPlan(request);
    }

    @TenantId
    @PostMapping("/getOaAnnualPlanList")
    public BaseResponse getOaAnnualPlanList(@RequestBody OaAnnualPlanDto request) {
        log.info("getOaAnnualPlanList");
        return oaAnnualPlanService.getOaAnnualPlanList(request);
    }

    @GetMapping("/getOaAnnualPlanById")
    public BaseResponse getOaAnnualPlanById(@RequestParam String id) {
        log.info("getOaAnnualPlanById");
        return oaAnnualPlanService.getOaAnnualPlanById(id);
    }

    @TenantId
    @PostMapping("/getOaAnnualPlanDtlList")
    public BaseResponse getOaAnnualPlanDtlList(@RequestBody OaAnnualPlanDto request) {
        log.info("getOaAnnualPlanDtlList");
        return oaAnnualPlanDtlService.getOaAnnualPlanDtlList(request);
    }

}
