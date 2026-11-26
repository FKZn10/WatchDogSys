package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/procurementPlan")
public class SecProcurementPlanController {

    @Resource
    private ISecProcurementPlanService secProcurementPlanService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecProcurementPlan")
    public BaseResponse createOrUpdateSecProcurementPlan(@RequestBody SecProcurementPlanDto request) {
        log.info("createOrUpdateSecProcurementPlan");
        return secProcurementPlanService.createOrUpdateSecProcurementPlan(request);
    }


    /**
     * 获取项目概算树（项目概算）
     * @param pId
     * @return
     */
    @GetMapping("/getSecProcurementPlanAmountTree")
    public BaseResponse getSecProcurementPlanAmountTree(@RequestParam String pId) {
        return secProcurementPlanService.getSecProcurementPlanAmountTree(pId);
    }


    @TenantId
    @PostMapping("/getSecProcurementPlanList")
    public BaseResponse getSecProcurementPlanList(@RequestBody SecProcurementPlanDto request, @RequestHeader String token) {
        log.info("getSecProcurementPlanList");
        return secProcurementPlanService.getSecProcurementPlanList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecProcurementPlanById")
    public BaseResponse getSecProcurementPlanById(@RequestParam String ppId) {
        log.info("getSecProcurementPlanById");
        return secProcurementPlanService.getSecProcurementPlanById(ppId);
    }

    @GetMapping("/getSecProcurementPlanByPid")
    public BaseResponse getSecProcurementPlanByPid(@RequestParam String pId) {
        log.info("getSecProcurementPlanByPid");
        return secProcurementPlanService.getSecProcurementPlanByPid(pId);
    }


}
