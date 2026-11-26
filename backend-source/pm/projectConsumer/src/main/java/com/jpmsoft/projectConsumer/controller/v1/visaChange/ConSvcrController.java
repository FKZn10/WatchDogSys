package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvcrRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-现场签证变更记录", value = "变更签证-现场签证变更记录")
@RequestMapping("/v1/conSvcr")
public class ConSvcrController {
    @Autowired
    private VisaFeign visaFeign;


    @ApiOperation(value = "新增或修改现场签证变更记录")
    @PostMapping("/createOrUpdateConSvcr")
    public BaseResponse createOrUpdateConSvcr(@RequestBody ConSvcrRequest request) {
        log.info("createOrUpdateConSvcr");
        return visaFeign.createOrUpdateConSvcr(request);
    }

    @ApiOperation(value = "分页查询现场签证变更记录")
    @PostMapping("/getConSvcrList")
    public BaseResponse getConSvcrList(@RequestBody ConSvcrRequest request, @RequestHeader String token) {
        log.info("getConSvcrList");
        return visaFeign.getConSvcrList(request, token);
    }

    @ApiOperation(value = "ID查询现场签证变更记录")
    @GetMapping("/getConSvcrById")
    public BaseResponse getConSvcrById(@RequestParam String svcrId) {
        log.info("getConSvcrById");
        return visaFeign.getConSvcrById(svcrId);
    }
}
