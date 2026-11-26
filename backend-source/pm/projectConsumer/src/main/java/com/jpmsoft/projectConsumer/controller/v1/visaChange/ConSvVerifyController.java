package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvVerifyRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-现场签证工程量核实", value = "变更签证-现场签证工程量核实")
@RequestMapping("/v1/conSvVerify")
public class ConSvVerifyController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改现场签证工程量核实")
    @PostMapping("/createOrUpdateConSvVerify")
    public BaseResponse createOrUpdateConSvVerify(@RequestBody ConSvVerifyRequest request) {
        log.info("createOrUpdateConSvVerify");
        return visaFeign.createOrUpdateConSvVerify(request);
    }

    @ApiOperation(value = "分页查询现场签证工程量核实")
    @PostMapping("/getConSvVerifyList")
    public BaseResponse getConSvVerifyList(@RequestBody ConSvVerifyRequest request, @RequestHeader String token) {
        log.info("getConSvVerifyList");
        return visaFeign.getConSvVerifyList(request, token);
    }

    @ApiOperation(value = "ID查询现场签证工程量核实")
    @GetMapping("/getConSvVerifyById")
    public BaseResponse getConSvVerifyById(@RequestParam String svvId) {
        log.info("getConSvVerifyById");
        return visaFeign.getConSvVerifyById(svvId);
    }
}
