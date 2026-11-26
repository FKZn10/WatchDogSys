package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 13:41
 */
@Slf4j
@RestController
@Api(tags = "施工图审查", value = "施工图审查")
@RequestMapping("/design/v1/designDrawReview")
public class DesignDrawReviewController {

    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改施工图审查")
    @PostMapping("/createOrUpdateDesignDrawReview")
    public BaseResponse createOrUpdateDesignDrawReview(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token) {
        log.info("createOrUpdateDesignDrawReview");
        return baseFeign.createOrUpdateDesignDrawReview(request, token);
    }

    @ApiOperation(value = "分页查询施工图审查")
    @PostMapping("/getDesignDrawReviewList")
    public BaseResponse getDesignDrawReviewList(@RequestBody DesignDrawReviewDTO request, @RequestHeader String token) {
        log.info("getDesignDrawReviewList");
        return baseFeign.getDesignDrawReviewList(request, token);
    }

    @ApiOperation(value = "ID查询施工图审查")
    @GetMapping("/getDesignDrawReviewById")
    public BaseResponse getDesignDrawReviewById(@RequestParam String id) {
        log.info("getDesignDrawReviewById");
        return baseFeign.getDesignDrawReviewById(id);
    }

}
