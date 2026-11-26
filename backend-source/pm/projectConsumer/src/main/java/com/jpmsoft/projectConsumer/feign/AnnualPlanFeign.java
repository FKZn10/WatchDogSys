package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "AnnualPlan")
public interface AnnualPlanFeign {
    @PostMapping("/v1/annualPlan/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody AnnualPlanModel model);
    @PostMapping("/v1/annualPlan/approvalData")
    BaseResponse approvalData(@RequestBody AnnualPlanModel request);
    @PostMapping("/v1/annualPlan/getList")
    BaseResponse getList(@RequestBody AnnualPlanModel request, @RequestHeader String token);
    @GetMapping("/v1/annualPlan/getDetail")
    BaseResponse getDetail(@RequestParam("id") String id);
    @PostMapping("/v1/annualPlan/getByYear")
    BaseResponse getByYear(@RequestBody AnnualPlanModel request);
    @PostMapping("/v1/annualPlan/getInfo")
    BaseResponse getInfo(@RequestBody AnnualPlanModel request);
}