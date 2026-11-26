package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreCommandPostService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/commandPost")
public class PreCommandPostController {

    @Resource
    private IPreCommandPostService preCommandPostService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreCommandPost")
    public BaseResponse createOrUpdatePreCommandPost(@RequestBody PreCommandPostModel request) {
        log.info("createOrUpdatePreCommandPost");
        return preCommandPostService.createOrUpdatePreCommandPost(request);
    }

    @TenantId
    @PostMapping("/getPreCommandPostList")
    public BaseResponse getPreCommandPostList(@RequestBody PreCommandPostModel request, @RequestHeader String token) {
        log.info("getPreCommandPostList");
        return preCommandPostService.getPreCommandPostList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPreCommandPostStatisticsList")
    public BaseResponse getPreCommandPostStatisticsList(@RequestBody PreCommandPostModel request, @RequestHeader String token) {
        log.info("getPreCommandPostStatisticsList");
        return preCommandPostService.getPreCommandPostStatisticsList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getPreCommandPostById")
    public BaseResponse getPreCommandPostById(@RequestParam String comId) {
        log.info("getPreCommandPostById");
        return preCommandPostService.getPreCommandPostById(comId);
    }
}
