package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "projectAcceptance")
public interface ProjectAcceptanceFeign {
    @PostMapping("/v1/projectAcceptance/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody ProjectAcceptanceCalculationAuditModel dto);
    @PostMapping("/v1/projectAcceptance/approvalData")
    BaseResponse approvalData(@RequestBody ProjectAcceptanceCalculationAuditModel request);
    @PostMapping("/v1/projectAcceptance/getList")
    BaseResponse getList(@RequestBody ProjectAcceptanceCalculationAuditModel request,@RequestHeader String token);
    @GetMapping("/v1/projectAcceptance/getDetail")
    BaseResponse getDetail(@RequestParam("id")String id);
    @PostMapping("/v1/projectAcceptance/approvalData2")
    BaseResponse approvalData2(@RequestBody ProjectAcceptanceCalculationAuditModel request);
    @PostMapping("/v1/projectAcceptance/refreshAll")
    BaseResponse refreshAll(@RequestBody ProjectAcceptanceCalculationAuditModel request);
}