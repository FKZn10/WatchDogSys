package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yb.Sun
 * @date 2024/10/30 10:17
 */
@Slf4j
@RestController
@RequestMapping("/v1/InvestmentEstimateBreakdown")
public class InvestmentEstimateBreakdownController {
    @Autowired
    private InvFeign invFeign;
    @PostMapping("/createOrUpdateInvestmentEstimateBreakdown")
    public BaseResponse createOrUpdateInvestmentEstimateBreakdown(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("createOrUpdateInvestmentEstimateBreakdown");
        return invFeign.createOrUpdateInvestmentEstimateBreakdown(request);
    }
    @PostMapping("/getInvestmentEstimateBreakdownList")
    public BaseResponse getInvestmentEstimateBreakdownList(@RequestBody InvestmentEstimateBreakdownModel request, @RequestHeader String token) {
        log.info("getInvestmentEstimateBreakdownList");
        return invFeign.getInvestmentEstimateBreakdownList(request,token);
    }


    @GetMapping("/getInvestmentEstimateBreakdownById")
    public BaseResponse getInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("getInvestmentEstimateBreakdownById");
        return invFeign.getInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/getLatestInvestmentEstimateBreakdownById")
    public BaseResponse getLatestInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("getLatestInvestmentEstimateBreakdownById");
        return invFeign.getLatestInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/getInvestmentEstimateBreakdownByProjectId")
    public BaseResponse getInvestmentEstimateBreakdownByProjectId(@RequestParam String projectId) {
        log.info("getInvestmentEstimateBreakdownByProjectId");
        return invFeign.getInvestmentEstimateBreakdownByProjectId(projectId);
    }



    @GetMapping("/deleteInvestmentEstimateBreakdownById")
    public BaseResponse deleteInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("deleteInvestmentEstimateBreakdownById");
        return invFeign.deleteInvestmentEstimateBreakdownById(id);
    }

    @GetMapping("/removeInvestmentEstimateBreakdownById")
    public BaseResponse removeInvestmentEstimateBreakdownById(@RequestParam String id) {
        log.info("removeInvestmentEstimateBreakdownById");
        return invFeign.removeInvestmentEstimateBreakdownById(id);
    }
    @PostMapping("/getProjectIdList")
    public BaseResponse getProjectIdList(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("getProjectIdList");
        return invFeign.getProjectIdList(request);
    }

    @GetMapping("/getInvestmentEstimateBreakdownByPId")
    public BaseResponse getInvestmentEstimateBreakdownByPId(@RequestParam String pId) {
        log.info("getInvestmentEstimateBreakdownByPId");
        return invFeign.getInvestmentEstimateBreakdownByPId(pId);
    }

    @PostMapping("/getUnusedProjectIdList")
    public BaseResponse getUnusedProjectIdList(@RequestBody InvestmentEstimateBreakdownModel request) {
        log.info("getProjectIdList");
        log.info("InvestmentEstimateBreakdownModel:{}", JacksonUtils.toJSONString(request));
        return invFeign.getUnusedProjectIdList(request);
    }

}
