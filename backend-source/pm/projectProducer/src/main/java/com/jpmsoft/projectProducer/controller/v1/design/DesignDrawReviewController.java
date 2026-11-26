package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectProducer.service.design.IDesignDrawReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 13:41
 */
@Slf4j
@RestController
@RequestMapping("/v1/designDrawReview")
public class DesignDrawReviewController {

    @Resource
    private IDesignDrawReviewService designDrawReviewService;

    @TenantId
    @PostMapping("/createOrUpdateDesignDrawReview")
    public BaseResponse createOrUpdateDesignDrawReview(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token) {
        log.info("createOrUpdateDesignDrawReview");
        return designDrawReviewService.createOrUpdateDesignDrawReview(request, token);
    }

    @TenantId
    @PostMapping("/getDesignDrawReviewList")
    public BaseResponse getDesignDrawReviewList(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token) {
        log.info("getDesignDrawReviewList");
        return designDrawReviewService.getDesignDrawReviewList(request, token);
    }

    @GetMapping("/getDesignDrawReviewById")
    public BaseResponse getDesignDrawReviewById(@RequestParam String id) {
        log.info("getDesignDrawReviewById");
        return designDrawReviewService.getDesignDrawReviewById(id);
    }

}
