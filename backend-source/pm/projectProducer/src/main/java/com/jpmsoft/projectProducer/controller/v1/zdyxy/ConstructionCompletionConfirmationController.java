package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionCompletionConfirmModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.zdyxy.ConstructionCompletionConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/constructionCompletionConfirm/")
@Slf4j
public class ConstructionCompletionConfirmationController {
    @Autowired
    private ConstructionCompletionConfirmService constructionCompletionConfirmService;
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody ConstructionCompletionConfirmModel model) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(model));
        return constructionCompletionConfirmService.saveOrUpdateData(model);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ConstructionCompletionConfirmModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return constructionCompletionConfirmService.approvalData(request);
    }


    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ConstructionCompletionConfirmModel request, @RequestHeader String token) {
        log.info("查询列表:{}", JacksonUtils.toJSONString(request));
        return constructionCompletionConfirmService.getList(request,token);
    }


    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id")String id) {
        log.info("查询结果:{}", id);
        return constructionCompletionConfirmService.getDetail(id);
    }
}