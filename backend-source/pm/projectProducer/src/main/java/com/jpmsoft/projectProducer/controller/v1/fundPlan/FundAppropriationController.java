package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDto;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAppropriationDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAppropriationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/17 10:36
 */
@Slf4j
@RestController
@RequestMapping("/v1/fundAppropriation")
public class FundAppropriationController {

    @Resource
    private IFundAppropriationService fundAppropriationService;
    @Resource
    private IFundAppropriationDtlService fundAppropriationDtlService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateFundAppropriation")
    public BaseResponse createOrUpdateFundAppropriation(@RequestBody FundAppropriationDto request) {
        log.info("createOrUpdateFundAppropriation");
        return fundAppropriationService.createOrUpdateFundAppropriation(request);
    }

    @TenantId
    @PostMapping("/getFundAppropriationList")
    public BaseResponse getFundAppropriationList(@RequestBody FundAppropriationDto request, @RequestHeader String token) {
        log.info("getFundAppropriationList");
        return fundAppropriationService.getFundAppropriationList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getFundAppropriationById")
    public BaseResponse getFundAppropriationById(@RequestParam String id) {
        log.info("getFundAppropriationById");
        return fundAppropriationService.getFundAppropriationById(id);
    }


    /**
     * 获取项目前期已完成投资=本期完成投资合计
     *
     * @param pId
     * @return
     */
    @GetMapping("/getProCompletedInvSum")
    public BaseResponse getProCompletedInvSum(@RequestParam String pId) {
        log.info("getProCompletedInvSum");
        return fundAppropriationService.getProCompletedInvSum(pId);
    }

    @GetMapping("/getFaDemandSumByPid")
    public BaseResponse getFaDemandSumByPid(@RequestParam String pId) {
        log.info("getFaDemandSumByPid");
        return fundAppropriationService.getFaDemandSumByPid(pId);
    }

    /**
     * 查询资金拨付详情指定合同前期已完成投资、前期已付资金
     * 和其对应资金计划详情的本期资金需求
     *
     * @param fpId
     * @param conId
     * @return
     */
    @GetMapping("/getConAppropriationDtl")
    public BaseResponse getConAppropriationDtl(@RequestParam(required = false) String fpId, @RequestParam String conId) {
        log.info("getConAppropriationDtl");
        return fundAppropriationService.getConAppropriationDtl(fpId, conId);
    }


    @GetMapping("/getAllFundPlanDtlByFpId")
    public BaseResponse getAllFundPlanDtlByFpId(@RequestParam String fpId) {
        log.info("getAllFundPlanDtlByFpId");
        return fundAppropriationService.getAllFundPlanDtlByFpId(fpId);
    }

    @TenantId
    @PostMapping("/getFundAppropriationDtlList")
    public BaseResponse getFundAppropriationDtlList(@RequestBody FundAppropriationDtlDto request) {
        log.info("getFundAppropriationDtlList");
        return fundAppropriationDtlService.getFundAppropriationDtlList(request);
    }
}
