package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertSupplementDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 15:19
 */
@Slf4j
@RestController
@Api(tags = "专家补抽", value = "专家补抽")
@RequestMapping("/v1/secExpertSupplement")
public class SecExpertSupplementController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家补抽")
    @PostMapping("/createOrUpdateExpertSupplement")
    public BaseResponse createOrUpdateExpertSupplement(@RequestBody SecExpertSupplementDTO request) {
        log.info("createOrUpdateExpertSupplement");
        return secFeign.createOrUpdateExpertSupplement(request);
    }

    @ApiOperation(value = "分页查询专家补抽")
    @PostMapping("/getExpertSupplementList")
    public BaseResponse getExpertSupplementList(@RequestBody SecExpertSupplementDTO request) {
        log.info("getExpertSupplementList");
        return secFeign.getExpertSupplementList(request);
    }

    @ApiOperation(value = "ID查询专家补抽")
    @GetMapping("/getExpertSupplementById")
    public BaseResponse getExpertSupplementById(@RequestParam String id) {
        log.info("getExpertSupplementById");
        return secFeign.getExpertSupplementById(id);
    }

    @ApiOperation(value = "审批通过同步补抽的专家")
    @GetMapping("/approveAppendSelection")
    public BaseResponse approveAppendSelection(@RequestParam String id) {
        log.info("approveAppendSelection");
        return secFeign.approveAppendSelection(id);
    }
}
