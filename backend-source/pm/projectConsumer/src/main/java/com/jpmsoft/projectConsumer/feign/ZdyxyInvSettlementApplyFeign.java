package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "project-producer", contextId = "invSettlementApply")
public interface ZdyxyInvSettlementApplyFeign {
    @PostMapping("/v1/zdyxy/invSettlementApply/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody InvSettlementApplyModel request);
    @PostMapping("/v1/zdyxy/invSettlementApply/approvalData")
    BaseResponse approvalData(@RequestBody InvSettlementApplyModel request);
    @PostMapping("/v1/zdyxy/invSettlementApply/getList")
    BaseResponse getList(@RequestBody InvSettlementApplyModel request,@RequestHeader String token);
    @GetMapping("/v1/zdyxy/invSettlementApply/getDetail")
    BaseResponse getDetail(@RequestParam("saId")String saId);
}