package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IProImageProgressService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/imageProgress")
public class ProImageProgressController {

    @Resource
    private IProImageProgressService imageProgressService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateProImageProgress")
    public BaseResponse createOrUpdateProImageProgress(@RequestBody ProImageProgressModel request) {
        log.info("createOrUpdateProImageProgress");
        return imageProgressService.createOrUpdateProImageProgress(request);
    }

    @TenantId
    @PostMapping("/getProImageProgressList")
    public BaseResponse getProImageProgressList(@RequestBody ProImageProgressModel request, @RequestHeader String token) {
        log.info("getProImageProgressList");
        return imageProgressService.getProImageProgressList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProImageProgressById")
    public BaseResponse getProImageProgressById(@RequestParam String id, @RequestHeader String token) {
        log.info("getProImageProgressById");
        return imageProgressService.getProImageProgressById(id, redisUtils.getUid(token));
    }

    @GetMapping("/deleteProImageProgressById")
    public BaseResponse deleteProImageProgressById(@RequestParam String id) {
        log.info("getProImageProgressById");
        return imageProgressService.deleteProImageProgressById(id);
    }
}
