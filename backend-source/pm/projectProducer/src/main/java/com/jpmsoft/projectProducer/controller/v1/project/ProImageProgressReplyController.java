package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressReplyModel;
import com.jpmsoft.projectProducer.service.project.IProImageProgressReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/imageProgressReply")
public class ProImageProgressReplyController {

    @Resource
    private IProImageProgressReplyService imageProgressReplyService;

    @PostMapping("/createProImageProgressReply")
    public BaseResponse createProImageProgressReply(@RequestBody ProImageProgressReplyModel request) {
        log.info("createProImageProgressReply");
        return imageProgressReplyService.createProImageProgressReply(request);
    }

    @GetMapping("/deleteProImageProgressReplyById")
    public BaseResponse deleteProImageProgressReplyById(@RequestParam String id) {
        log.info("deleteProImageProgressReplyById");
        return imageProgressReplyService.deleteProImageProgressReplyById(id);
    }
}
