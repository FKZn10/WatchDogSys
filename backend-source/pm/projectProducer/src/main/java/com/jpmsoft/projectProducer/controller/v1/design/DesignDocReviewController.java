package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectProducer.service.design.IDesignDocReviewService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:48
 */
@Slf4j
@RestController
@RequestMapping("/v1/designDocReview")
public class DesignDocReviewController {
    @Resource
    private IDesignDocReviewService designDocReviewService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDocReview")
    public BaseResponse createOrUpdateDocReview(@RequestBody DesignDocReviewDTO request) {
        log.info("createOrUpdateDocReview");
        return designDocReviewService.createOrUpdateDocReview(request);
    }

    @TenantId
    @PostMapping("/getDesignDocReviewList")
    public BaseResponse getDesignDocReviewList(@RequestBody DesignDocReviewDTO request, @RequestHeader String token) {
        log.info("getDesignDocReviewList");
        return designDocReviewService.getDesignDocReviewList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getDesignDocReviewById")
    public BaseResponse getDesignDocReviewById(@RequestParam String id) {
        log.info("getDesignDocReviewById");
        return designDocReviewService.getDesignDocReviewById(id);
    }
}
