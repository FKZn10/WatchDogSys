package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvestmentControlRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownModel;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yb.Sun
 * @date 2024/11/1 14:55
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentBudgetBreakdown")
public class InvestmentBudgetBreakdownController {
    @Autowired
    private InvFeign invFeign;
    @PostMapping("/createOrUpdateInvestmentBudgetBreakdown")
    public BaseResponse createOrUpdateInvestmentBudgetBreakdown(@RequestBody InvestmentBudgetBreakdownModel request) {
        log.info("createOrUpdateInvestmentBudgetBreakdown");
        return invFeign.createOrUpdateInvestmentBudgetBreakdown(request);
    }
    @PostMapping("/getInvestmentBudgetBreakdownList")
    public BaseResponse getInvestmentBudgetBreakdownList(@RequestBody InvestmentBudgetBreakdownModel request, @RequestHeader String token) {
        log.info("getInvestmentBudgetBreakdownList");
        return invFeign.getInvestmentBudgetBreakdownList(request,token);
    }


    @GetMapping("/getInvestmentBudgetBreakdownById")
    public BaseResponse getInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownById");
        return invFeign.getInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/getLatestInvestmentBudgetBreakdownById")
    public BaseResponse getLatestInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("getInvestmentBudgetBreakdownById");
        return invFeign.getLatestInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/deleteInvestmentBudgetBreakdownById")
    public BaseResponse deleteInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("deleteInvestmentBudgetBreakdownById");
        return invFeign.deleteInvestmentBudgetBreakdownById(id);
    }

    @GetMapping("/removeInvestmentBudgetBreakdownById")
    public BaseResponse removeInvestmentBudgetBreakdownById(@RequestParam String id) {
        log.info("removeInvestmentBudgetBreakdownById");
        return invFeign.removeInvestmentBudgetBreakdownById(id);
    }


    @GetMapping("/getInvestmentBudgetBreakdownByPId")
    public BaseResponse getInvestmentBudgetBreakdownByPId(@RequestParam String pId) {
        log.info("getInvestmentBudgetBreakdownByPId");
        return invFeign.getInvestmentBudgetBreakdownByPId(pId);
    }

    @GetMapping("/getInvestmentSecInfoByPId")
    public BaseResponse getInvestmentSecInfoByPId(@RequestParam String pId) {
        log.info("getInvestmentSecInfoByPId");
        return invFeign.getInvestmentSecInfoByPId(pId);
    }

    /**
     * 投资控制实时监控列表
     */
    @PostMapping("/getInvestmentControlList")
    public BaseResponse getInvestmentControlList(@RequestBody InvestmentControlRequest request){
        log.info("getInvestmentControlList");
        return invFeign.getInvestmentControlList(request);
    }
    //投资控制实时监控详情
    @GetMapping("/getInvestmentControlByPId")
    public BaseResponse getInvestmentControlByPId(@RequestParam String pId){
        log.info("getInvestmentControlByPId");
        return invFeign.getInvestmentControlByPId(pId);
    }

    //投资控制实时监控多条件查询
    @PostMapping("/getInvestmentControlClassInfo")
    public BaseResponse getInvestmentControlClassInfo(@RequestBody InvestmentControlRequest request){
        log.info("getInvestmentControlClassInfo");
        return invFeign.getInvestmentControlClassInfo(request);
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
        return invFeign.getInvestmentBudgetBreakdownTreeByPId(pId);
    }
}
