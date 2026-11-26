package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConDclsDTO;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 设计变更记录单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 18:16
 */
@Slf4j
@RestController
@Api(tags = "变更签证-设计变更记录单", value = "变更签证-设计变更记录单")
@RequestMapping("/v1/conDcls")
public class ConDclsController {

    @Resource
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改设计变更记录单")
    @PostMapping("/createOrUpdateConDcls")
    public BaseResponse createOrUpdateConDcls(@RequestBody ConDclsDTO request) {
        log.info("createOrUpdateConDcls");
        return visaFeign.createOrUpdateConDcls(request);
    }

    @ApiOperation(value = "分页查询设计变更记录单")
    @PostMapping("/getConDclsList")
    public BaseResponse getConDclsList(@RequestBody ConDclsDTO request, @RequestHeader String token) {
        log.info("getConDclsList");
        return visaFeign.getConDclsList(request, token);
    }

    @ApiOperation(value = "ID查询设计变更记录单")
    @GetMapping("/getConDclsById")
    public BaseResponse getConDclsById(@RequestParam String id) {
        log.info("getConDclsById");
        return visaFeign.getConDclsById(id);
    }
}
