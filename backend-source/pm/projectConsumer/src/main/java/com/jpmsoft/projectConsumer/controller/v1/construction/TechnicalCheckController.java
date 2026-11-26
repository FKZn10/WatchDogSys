package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:31
 */
@Slf4j
@RestController
@Api(tags = "技术核定单", value = "技术核定单")
@RequestMapping("/v1/technicalCheck")
public class TechnicalCheckController {

    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改技术核定单")
    @PostMapping("/createOrUpdateTechnicalCheck")
    public BaseResponse createOrUpdateTechnicalCheck(@RequestBody TechnicalCheckDTO request) {
        log.info("createOrUpdateTechnicalCheck");
        return baseFeign.createOrUpdateTechnicalCheck(request);
    }

    @ApiOperation(value = "分页查询技术核定单")
    @PostMapping("/getTechnicalCheckList")
    public BaseResponse getTechnicalCheckList(@RequestBody TechnicalCheckDTO request, @RequestHeader String token) {
        log.info("getTechnicalCheckList");
        return baseFeign.getTechnicalCheckList(request, token);
    }

    @ApiOperation(value = "ID查询技术核定单")
    @GetMapping("/getTechnicalCheckById")
    public BaseResponse getTechnicalCheckById(@RequestParam String id) {
        log.info("getTechnicalCheckById");
        return baseFeign.getTechnicalCheckById(id);
    }

}
