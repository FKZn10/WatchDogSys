package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:48
 */
@Slf4j
@RestController
@Api(tags = "设计文件审核", value = "设计文件审核")
@RequestMapping("/design/v1/designDocReview")
public class DesignDocReviewController {
    @Resource
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改设计文件审核")
    @PostMapping("/createOrUpdateDocReview")
    public BaseResponse createOrUpdateDocReview(@RequestBody DesignDocReviewDTO request) {
        log.info("createOrUpdateDocReview");
        return baseFeign.createOrUpdateDocReview(request);
    }

    @ApiOperation(value = "分页查询设计文件审核")
    @PostMapping("/getDesignDocReviewList")
    public BaseResponse getDesignDocReviewList(@RequestBody DesignDocReviewDTO request, @RequestHeader String token) {
        log.info("getDesignDocReviewList");
        return baseFeign.getDesignDocReviewList(request, token);
    }


    @ApiOperation(value = "ID查询设计文件审核")
    @GetMapping("/getDesignDocReviewById")
    public BaseResponse getDesignDocReviewById(@RequestParam String id) {
        log.info("getDesignDocReviewById");
        return baseFeign.getDesignDocReviewById(id);
    }
}
