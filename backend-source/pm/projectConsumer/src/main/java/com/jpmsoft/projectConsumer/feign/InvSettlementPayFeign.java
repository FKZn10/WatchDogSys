package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "invSettlementPay")
public interface InvSettlementPayFeign {
    @PostMapping("/v1/invSettlementPay/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody InvSettlementPayModel model);
    @PostMapping("/v1/invSettlementPay/approvalData")
    BaseResponse approvalData(@RequestBody InvSettlementPayModel request);
    @PostMapping("/v1/invSettlementPay/getList")
    BaseResponse getList(@RequestBody InvSettlementPayModel request, @RequestHeader String token);
    @GetMapping("/v1/invSettlementPay/getDetail")
    BaseResponse getDetail(@RequestParam("id") String id);
}