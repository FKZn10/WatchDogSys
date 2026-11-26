package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionDrawingModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "constructionDrawing")
public interface ConstructionDrawingFeign {
    @PostMapping("/v1/constructionDrawing/saveOrUpdateData")
    BaseResponse saveOrUpdateData(@RequestBody ConstructionDrawingModel model);
    @PostMapping("/v1/constructionDrawing/approvalData")
    BaseResponse approvalData(@RequestBody ConstructionDrawingModel request);
    @PostMapping("/v1/constructionDrawing/getList")
    BaseResponse getList(@RequestBody ConstructionDrawingModel request, @RequestHeader  String token);
    @GetMapping("/v1/constructionDrawing/getDetail")
    BaseResponse getDetail(@RequestParam("id") String id);
}
