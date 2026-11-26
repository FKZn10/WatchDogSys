package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConDccsDTO;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 设计变更联系单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 18:16
 */
@Slf4j
@RestController
@Api(tags = "变更签证-设计变更联系单", value = "变更签证-设计变更联系单")
@RequestMapping("/v1/conDccs")
public class ConDccsController {

    @Resource
    private VisaFeign visaFeign;


    @ApiOperation(value = "新增或修改设计变更联系单")
    @PostMapping("/createOrUpdateConDccs")
    public BaseResponse createOrUpdateConDccs(@RequestBody ConDccsDTO request) {
        log.info("createOrUpdateConDccs");
        return visaFeign.createOrUpdateConDccs(request);
    }


    @ApiOperation(value = "分页查询设计变更联系单")
    @PostMapping("/getConDccsList")
    public BaseResponse getConDccsList(@RequestBody ConDccsDTO request, @RequestHeader String token) {
        log.info("getConDccsList");
        return visaFeign.getConDccsList(request, token);
    }


    @ApiOperation(value = "ID查询设计变更联系单")
    @GetMapping("/getConDccsById")
    public BaseResponse getConDccsById(@RequestParam String id) {
        log.info("getConDccsById");
        return visaFeign.getConDccsById(id);
    }
}
