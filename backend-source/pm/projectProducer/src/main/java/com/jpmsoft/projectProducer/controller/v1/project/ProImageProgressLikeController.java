package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressLikeModel;
import com.jpmsoft.projectProducer.service.project.IProImageProgressLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/imageProgressLike")
public class ProImageProgressLikeController {

    @Resource
    private IProImageProgressLikeService imageProgressLikeService;

    @PostMapping("/createProImageProgressLike")
    public BaseResponse createProImageProgressLike(@RequestBody ProImageProgressLikeModel request) {
        log.info("createProImageProgressLike");
        return imageProgressLikeService.createProImageProgressLike(request);
    }

    @GetMapping("/deleteProImageProgressLikeById")
    public BaseResponse deleteProImageProgressLikeById(@RequestParam String id) {
        log.info("deleteProImageProgressLikeById");
        return imageProgressLikeService.deleteProImageProgressLikeById(id);
    }
}
