package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:57
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentEstimateBreakdown")
public class InvestmentEstimateBreakdownController {
    @Resource
    private IInvestmentEstimateBreakdownService investmentEstimateBreakdownService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateInvestmentEstimateBreakdown")
    public BaseResponse createOrUpdateInvestmentEstimateBreakdown(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("createOrUpdateInvestmentEstimateBreakdown");
        log.info("InvestmentEstimateBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentEstimateBreakdownService.createOrUpdateInvestmentEstimateBreakdown(request);
    }

    @PostMapping("/getInvestmentEstimateBreakdownList")
    public BaseResponse getInvestmentEstimateBreakdownList(@RequestBody InvestmentEstimateBreakdownModel request,@RequestHeader String token) {
        log.info("getInvestmentEstimateBreakdownList");
        log.info("InvestmentEstimateBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentEstimateBreakdownService.getInvestmentEstimateBreakdownList(request,redisUtils.getUid(token));
    }


    @GetMapping("/getInvestmentEstimateBreakdownById")
    public BaseResponse getInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("getInvestmentEstimateBreakdownById");
        return investmentEstimateBreakdownService.getInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/getLatestInvestmentEstimateBreakdownById")
    public BaseResponse getLatestInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("getInvestmentEstimateBreakdownById");
        return investmentEstimateBreakdownService.getLatestInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/getInvestmentEstimateBreakdownByProjectId")
    public BaseResponse getInvestmentEstimateBreakdownByProjectId(@RequestParam String projectId) {
        log.info("getInvestmentEstimateBreakdownByProjectId");
        return investmentEstimateBreakdownService.getInvestmentEstimateBreakdownByProjectId(projectId);
    }

    @GetMapping("/deleteInvestmentEstimateBreakdownById")
    public BaseResponse deleteInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("deleteInvestmentEstimateBreakdownById");
        return investmentEstimateBreakdownService.deleteInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/removeInvestmentEstimateBreakdownById")
    public BaseResponse removeInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("removeInvestmentEstimateBreakdownById");
        return investmentEstimateBreakdownService.removeInvestmentEstimateBreakdownById(id);
    }

    @PostMapping("/getProjectIdList")
    public BaseResponse getProjectIdList(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("getProjectIdList");
        log.info("InvestmentEstimateBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentEstimateBreakdownService.getProjectIdList(request);
    }

    @GetMapping("/getInvestmentEstimateBreakdownByPId")
    public BaseResponse getInvestmentEstimateBreakdownByPId(@RequestParam String pId) {
        log.info("getInvestmentEstimateBreakdownByPId");
        return investmentEstimateBreakdownService.getInvestmentEstimateBreakdownByPId(pId);
    }

    @PostMapping("/getUnusedProjectIdList")
    public BaseResponse getUnusedProjectIdList(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("getProjectIdList");
        log.info("InvestmentEstimateBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentEstimateBreakdownService.getUnusedProjectIdList(request);
    }

}
