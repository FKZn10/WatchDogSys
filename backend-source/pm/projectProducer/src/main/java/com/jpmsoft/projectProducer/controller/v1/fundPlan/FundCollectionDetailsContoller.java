package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundCollectionDetailsDto;
import com.jpmsoft.projectProducer.service.fundPlan.IFundCollectionDetailsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 16:10
 */
@Slf4j
@RestController
@RequestMapping("/v1/fundCollectionDetails")
public class FundCollectionDetailsContoller {

    @Resource
    private IFundCollectionDetailsService fundCollectionDetailsService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateFundCollectionDetails")
    public BaseResponse createOrUpdateFundCollectionDetails(@RequestBody FundCollectionDetailsDto request) {
        log.info("createOrUpdateFundCollectionDetails");
        return fundCollectionDetailsService.createOrUpdateFundCollectionDetails(request);
    }

    @TenantId
    @PostMapping("/getFundCollectionDetailsList")
    public BaseResponse getFundCollectionDetailsList(@RequestBody FundCollectionDetailsDto request, @RequestHeader String token) {
        log.info("getFundCollectionDetailsList");
        return fundCollectionDetailsService.getFundCollectionDetailsList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getFundCollectionProjectList")
    public BaseResponse getFundCollectionProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getFundCollectionProjectList");
        return fundCollectionDetailsService.getFundCollectionProjectList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getFundCollectionDetailsById")
    public BaseResponse getFundCollectionDetailsById(@RequestParam String id) {
        log.info("getFundCollectionDetailsById");
        return fundCollectionDetailsService.getFundCollectionDetailsById(id);
    }


    @GetMapping("/getLastFundCollectionDetailsByPid")
    public BaseResponse getLastFundCollectionDetailsByPid(@RequestParam String pId) {
        log.info("getLastFundCollectionDetailsByPid");
        return fundCollectionDetailsService.getLastFundCollectionDetailsByPid(pId);
    }

    @GetMapping("/statisticsBatchNoAndAmt")
    public BaseResponse statisticsBatchNoAndAmt(@RequestParam String pId) {
        log.info("statisticsBatchNoAndAmt");
        return fundCollectionDetailsService.statisticsBatchNoAndAmt(pId);
    }

    @TenantId
    @PostMapping("/getProjectFundCollectionDetailsList")
    public BaseResponse getProjectFundCollectionDetailsList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectFundCollectionDetailsList");
        return fundCollectionDetailsService.getProjectFundCollectionDetailsList(request, redisUtils.getUid(token));
    }
}
