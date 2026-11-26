package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnVerifyRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-工程量核实", value = "变更签证-工程量核实")
@RequestMapping("/v1/conEnVerify")
public class ConEnVerifyController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改工程量核实")
    @PostMapping("/createOrUpdateConEnVerify")
    public BaseResponse createOrUpdateConEnVerify(@RequestBody ConEnVerifyRequest request) {
        log.info("createOrUpdateConEnVerify");
        return visaFeign.createOrUpdateConEnVerify(request);
    }

    @ApiOperation(value = "分页查询工程量核实")
    @PostMapping("/getConEnVerifyList")
    public BaseResponse getConEnVerifyList(@RequestBody ConEnVerifyRequest request, @RequestHeader String token) {
        log.info("getConEnVerifyList");
        return visaFeign.getConEnVerifyList(request, token);
    }

    @ApiOperation(value = "ID查询工程量核实")
    @GetMapping("/getConEnVerifyById")
    public BaseResponse getConEnVerifyById(@RequestParam String envId) {
        log.info("getConEnVerifyById");
        return visaFeign.getConEnVerifyById(envId);
    }
}
