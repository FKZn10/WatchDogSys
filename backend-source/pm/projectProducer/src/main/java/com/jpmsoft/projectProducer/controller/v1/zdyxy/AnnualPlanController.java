package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.zdyxy.AnnualPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/annualPlan")
@Slf4j
public class AnnualPlanController {
    @Autowired
    private AnnualPlanService annualPlanService;

    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody AnnualPlanModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return annualPlanService.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody AnnualPlanModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return annualPlanService.approvalData(request);
    }
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AnnualPlanModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return annualPlanService.getList(request, token);
    }
    @PostMapping("/getByYear")
    public BaseResponse getByYear(@RequestBody AnnualPlanModel request) {
        log.info("getByYear:{}", JacksonUtils.toJSONString(request));
        return annualPlanService.getByYear(request);
    }
    @PostMapping("/getInfo")
    public BaseResponse getInfo(@RequestBody AnnualPlanModel request) {
        log.info("getInfo:{}", JacksonUtils.toJSONString(request));
        return annualPlanService.getInfo(request);
    }
    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id") String id) {
        log.info("getDetail");
        return annualPlanService.getDetail(id);
    }
}