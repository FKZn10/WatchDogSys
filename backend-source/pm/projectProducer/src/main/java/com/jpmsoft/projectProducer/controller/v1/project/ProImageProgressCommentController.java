package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressCommentModel;
import com.jpmsoft.projectProducer.service.project.IProImageProgressCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/imageProgressComment")
public class ProImageProgressCommentController {

    @Resource
    private IProImageProgressCommentService imageProgressCommentService;

    @PostMapping("/createProImageProgressComment")
    public BaseResponse createProImageProgressComment(@RequestBody ProImageProgressCommentModel request) {
        log.info("createProImageProgressComment");
        return imageProgressCommentService.createProImageProgressComment(request);
    }

    @GetMapping("/getProImageProgressCommentListByIpId")
    public BaseResponse getProImageProgressCommentListByIpId(@RequestParam String request) {
        log.info("getProImageProgressList");
        return imageProgressCommentService.getProImageProgressCommentListByIpId(request);
    }

    @GetMapping("/getProImageProgressCommentById")
    public BaseResponse getProImageProgressCommentById(@RequestParam String id) {
        log.info("getProImageProgressCommentById");
        return imageProgressCommentService.getProImageProgressCommentById(id);
    }

    @GetMapping("/deleteProImageProgressCommentById")
    public BaseResponse deleteProImageProgressCommentById(@RequestParam String id) {
        log.info("deleteProImageProgressCommentById");
        return imageProgressCommentService.deleteProImageProgressCommentById(id);
    }
}
