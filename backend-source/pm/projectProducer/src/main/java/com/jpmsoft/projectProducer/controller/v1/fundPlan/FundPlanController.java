package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDto;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationVo;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资金计划表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 15:52
 */
@Slf4j
@RestController
@RequestMapping("/v1/fundPlan")
public class FundPlanController {

    @Resource
    private IFundPlanService fundPlanService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IFundPlanDtlService fundPlanDtlService;

    @TenantId
    @PostMapping("/createOrUpdateFundPlan")
    public BaseResponse createOrUpdateFundPlan(@RequestBody FundPlanDto request) {
        log.info("createOrUpdateFundPlan");
        return fundPlanService.createOrUpdateFundPlan(request);
    }

    @TenantId
    @PostMapping("/getFundPlanList")
    public BaseResponse getFundPlanList(@RequestBody FundPlanDto request, @RequestHeader String token) {
        log.info("getFundPlanList");
        return fundPlanService.getFundPlanList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getFundPlanById")
    public BaseResponse getFundPlanById(@RequestParam String id) {
        log.info("getFundPlanById");
        return fundPlanService.getFundPlanById(id);
    }

    @GetMapping("/getProFpDemandFinalSum")
    public BaseResponse getProFpDemandFinalSum(@RequestParam String pId) {
        log.info("getProFpDemandFinalSum");
        return fundPlanService.getProFpDemandFinalSum(pId);
    }

    @TenantId
    @PostMapping("/getFundPlanDtlList")
    public BaseResponse getFundPlanDtlList(@RequestBody FundPlanDtlDto request) {
        log.info("getFundPlanDtlList");
        return fundPlanDtlService.getFundPlanDtlList(request);
    }


    @TenantId
    @GetMapping("/getFundPlanDtlByFpId")
    public BaseResponse getFundPlanDtlByFpId(@RequestParam String fpId) {
        log.info("getFundPlanDtlByFpId");
        return BaseResponse.ok(fundPlanDtlService.getAllFundPlanDtlByFpId(fpId));
    }

}
