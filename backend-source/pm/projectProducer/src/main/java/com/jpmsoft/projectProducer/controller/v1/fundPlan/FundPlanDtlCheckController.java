package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlCheckDto;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 资金计划表审核记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 15:51
 */
@Slf4j
@RestController
@RequestMapping("/v1/fundPlanDtlCheck")
public class FundPlanDtlCheckController {

    @Resource
    private IFundPlanDtlCheckService fundPlanDtlCheckService;

    @TenantId
    @PostMapping("/createOrUpdateFundPlanDtlCheck")
    public BaseResponse createOrUpdateFundPlanDtlCheck(@RequestBody FundPlanDtlCheckDto request) {
        log.info("createOrUpdateFundPlanDtlCheck");
        return fundPlanDtlCheckService.createOrUpdateFundPlanDtlCheck(request);
    }

    @TenantId
    @PostMapping("/getFundPlanDtlCheckList")
    public BaseResponse getFundPlanDtlCheckList(@RequestBody FundPlanDtlCheckDto request) {
        log.info("getFundPlanDtlCheckList");
        return fundPlanDtlCheckService.getFundPlanDtlCheckList(request);
    }

    @GetMapping("/getFundPlanDtlCheckById")
    public BaseResponse getFundPlanDtlCheckById(@RequestParam String id) {
        log.info("getFundPlanDtlCheckById");
        return fundPlanDtlCheckService.getFundPlanDtlCheckById(id);
    }
}
