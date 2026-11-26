package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionDrawingModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.zdyxy.ConstructionDrawingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/constructionDrawing")
@Slf4j
public class ConstructionDrawingController {
    @Autowired
    private ConstructionDrawingService constructionDrawingService;
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody ConstructionDrawingModel model) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(model));
        return constructionDrawingService.saveOrUpdateData(model);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ConstructionDrawingModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return constructionDrawingService.approvalData(request);
    }


    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ConstructionDrawingModel request, @RequestHeader String token) {
        log.info("查询列表:{}", JacksonUtils.toJSONString(request));
        return constructionDrawingService.getList(request,token);
    }


    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id")String id) {
        log.info("查询结果:{}", id);
        return constructionDrawingService.getDetail(id);
    }
}
