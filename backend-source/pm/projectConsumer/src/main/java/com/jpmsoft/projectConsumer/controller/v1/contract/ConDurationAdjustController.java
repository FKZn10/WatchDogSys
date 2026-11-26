package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConDurationAdjustDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:53
 */
@Slf4j
@RestController
@Api(tags = "工期调整", value = "工期调整")
@RequestMapping("/v1/conDurationAdjust")
public class ConDurationAdjustController {
    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改工期调整")
    @PostMapping("/createOrUpdateConDurationAdjust")
    public BaseResponse createOrUpdateConDurationAdjust(@RequestBody ConDurationAdjustDto request) {
        log.info("createOrUpdateConDurationAdjust");
        return conFeign.createOrUpdateConDurationAdjust(request);
    }

    @ApiOperation(value = "分页查询工期调整（带统计合同金额）")
    @PostMapping("/getConDurationAdjustList")
    public BaseResponse getConDurationAdjustList(@RequestBody ConDurationAdjustDto request, @RequestHeader String token) {
        log.info("getConDurationAdjustList");
        return conFeign.getConDurationAdjustList(request, token);
    }

    @ApiOperation(value = "ID查询工期调整")
    @GetMapping("/getConDurationAdjustById")
    public BaseResponse getConDurationAdjustById(@RequestParam String id) {
        log.info("getConDurationAdjustById");
        return conFeign.getConDurationAdjustById(id);
    }
}
