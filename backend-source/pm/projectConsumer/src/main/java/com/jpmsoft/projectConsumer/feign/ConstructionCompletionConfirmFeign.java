package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionCompletionConfirmModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "constructionCompletionConfirm")
public interface ConstructionCompletionConfirmFeign {
    @PostMapping("/v1/constructionCompletionConfirm/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody ConstructionCompletionConfirmModel model);
    @PostMapping("/v1/constructionCompletionConfirm/approvalData")
    BaseResponse approvalData(@RequestBody ConstructionCompletionConfirmModel request);
    @PostMapping("/v1/constructionCompletionConfirm/getList")
    BaseResponse getList(@RequestBody ConstructionCompletionConfirmModel request, @RequestHeader String token);
    @GetMapping("/v1/constructionCompletionConfirm/getDetail")
    BaseResponse getDetail(@RequestParam("id") String id);
}
