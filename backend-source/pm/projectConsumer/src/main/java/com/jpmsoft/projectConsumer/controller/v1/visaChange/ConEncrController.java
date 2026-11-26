package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEncrRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-工程洽商变更记录", value = "变更签证-工程洽商变更记录")
@RequestMapping("/v1/conEncr")
public class ConEncrController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改工程洽商变更记录")
    @PostMapping("/createOrUpdateConEncr")
    public BaseResponse createOrUpdateConEncr(@RequestBody ConEncrRequest request) {
        log.info("createOrUpdateConEncr");
        return visaFeign.createOrUpdateConEncr(request);
    }

    @ApiOperation(value = "分页查询工程洽商变更记录")
    @PostMapping("/getConEncrList")
    public BaseResponse getConEncrList(@RequestBody ConEncrRequest request, @RequestHeader String token) {
        log.info("getConEncrList");
        return visaFeign.getConEncrList(request, token);
    }

    @ApiOperation(value = "分页查询工程洽商变更记录-不鉴权")
    @PostMapping("/getConEncrListNoAuth")
    public BaseResponse getConEncrListNoAuth(@RequestBody ConEncrRequest request) {
        log.info("getConEncrListNoAuth");
        return visaFeign.getConEncrListNoAuth(request);
    }

    @ApiOperation(value = "ID查询工程洽商变更记录")
    @GetMapping("/getConEncrById")
    public BaseResponse getConEncrById(@RequestParam String encrId) {
        log.info("getConEncrById");
        return visaFeign.getConEncrById(encrId);
    }
}
