package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvestmentControlRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yb.Sun
 * @date 2024/11/1 16:20
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentBudgetBreakdown")
public class InvestmentBudgetBreakdownController{

    @Resource
    private IInvestmentBudgetBreakdownService investmentBudgetBreakdownService;
    @Resource
    private RedisUtils redisUtils;
    @PostMapping("/createOrUpdateInvestmentBudgetBreakdown")
    public BaseResponse createOrUpdateInvestmentBudgetBreakdown(@RequestBody InvestmentBudgetBreakdownModel request) {
        log.info("createOrUpdateInvestmentBudgetBreakdown");
        log.info("InvestmentBudgetBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentBudgetBreakdownService.createOrUpdateInvestmentBudgetBreakdown(request);
    }
    @PostMapping("/getInvestmentBudgetBreakdownList")
    public BaseResponse getInvestmentBudgetBreakdownList(@RequestBody InvestmentBudgetBreakdownModel request,@RequestHeader String token) {
        log.info("getInvestmentBudgetBreakdownList");
        log.info("InvestmentBudgetBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownList(request,redisUtils.getUid(token));
    }


    @GetMapping("/getInvestmentBudgetBreakdownById")
    public BaseResponse getInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownById");
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/getLatestInvestmentBudgetBreakdownById")
    public BaseResponse getLatestInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownById");
        return investmentBudgetBreakdownService.getLatestInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/deleteInvestmentBudgetBreakdownById")
    public BaseResponse deleteInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("deleteInvestmentBudgetBreakdownById");
        return investmentBudgetBreakdownService.deleteInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/removeInvestmentBudgetBreakdownById")
    public BaseResponse removeInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("removeInvestmentBudgetBreakdownById");
        return investmentBudgetBreakdownService.removeInvestmentBudgetBreakdownById(id);
    }

    /**
     * 通过项目获取概算数据
     * @param pId
     * @return
     */
    @GetMapping("/getInvestmentBudgetBreakdownByPId")
    public BaseResponse getInvestmentBudgetBreakdownByPId(@RequestParam String pId) {
        log.info("getInvestmentBudgetBreakdownByPId");
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownByPId(pId);
    }

    /**
     * 将概算、估算数据组装成标段信息
     * @param pId
     * @return
     */
    @GetMapping("/getInvestmentSecInfoByPId")
    public BaseResponse getInvestmentSecInfoByPId(@RequestParam String pId) {
        log.info("getInvestmentSecInfoByPId");
        return investmentBudgetBreakdownService.getInvestmentSecInfoByPId(pId);
    }


    /**
     * 投资控制实时监控
     */
    @PostMapping("/getInvestmentControlList")
    public BaseResponse getInvestmentControlList(@RequestBody InvestmentControlRequest request){
        return investmentBudgetBreakdownService.getInvestmentControlList(request);
    }

    //投资控制详情
    @GetMapping("/getInvestmentControlByPId")
    public BaseResponse getInvestmentControlByPId(@RequestParam String pId){
        return investmentBudgetBreakdownService.getInvestmentControlByPId(pId);
    }

    //投资控制实时监控多条件查询
    @PostMapping("/getInvestmentControlClassInfo")
    public BaseResponse getInvestmentControlClassInfo(@RequestBody InvestmentControlRequest request){
        return investmentBudgetBreakdownService.getInvestmentControlClassInfo(request);
    }

    /**
     * 根据项目查询概算清单树结构
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvestmentBudgetBreakdownTreeByPId")
    public BaseResponse getInvestmentBudgetBreakdownTreeByPId(@RequestParam String pId) {
        log.info("getInvestmentBudgetBreakdownTreeByPId");
        return investmentBudgetBreakdownService.getInvestmentBudgetBreakdownTreeByPId(pId);
    }
}
