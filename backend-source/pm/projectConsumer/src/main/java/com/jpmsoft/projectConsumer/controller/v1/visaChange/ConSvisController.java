package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvisRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "变更签证-现场签证指令单", value = "变更签证-现场签证指令单")
@RequestMapping("/v1/conSvis")
public class ConSvisController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改现场签证指令单")
    @PostMapping("/createOrUpdateConSvis")
    public BaseResponse createOrUpdateConSvis(@RequestBody ConSvisRequest request) {
        log.info("createOrUpdateConSvis");
        return visaFeign.createOrUpdateConSvis(request);
    }

    @ApiOperation(value = "分页查询现场签证指令单")
    @PostMapping("/getConSvisList")
    public BaseResponse getConSvisList(@RequestBody ConSvisRequest request, @RequestHeader String token) {
        log.info("getConSvisList");
        return visaFeign.getConSvisList(request, token);
    }

    @ApiOperation(value = "ID查询现场签证指令单")
    @GetMapping("/getConSvisById")
    public BaseResponse getConSvisById(@RequestParam String svisId) {
        log.info("getConSvisById");
        return visaFeign.getConSvisById(svisId);
    }
}
