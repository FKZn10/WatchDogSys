package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEcmRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 专家论证会申请
 */
@Slf4j
@RestController
@Api(tags = "变更签证-专家论证会申请", value = "变更签证-专家论证会申请")
@RequestMapping("/v1/conEcm")
public class ConEcmController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改专家论证会申请")
    @PostMapping("/createOrUpdateConEcm")
    public BaseResponse createOrUpdateConEcm(@RequestBody ConEcmRequest request) {
        log.info("createOrUpdateConEcm");
        return visaFeign.createOrUpdateConEcm(request);
    }

    @ApiOperation(value = "分页查专家论证会申请")
    @PostMapping("/getConEcmList")
    public BaseResponse getConEcmList(@RequestBody ConEcmRequest request, @RequestHeader String token) {
        log.info("getConEcmList");
        return visaFeign.getConEcmList(request, token);
    }

    @ApiOperation(value = "分页查专家论证会申请(不鉴权)")
    @PostMapping("/getConEcmListNoAuth")
    public BaseResponse getConEcmListNoAuth(@RequestBody ConEcmRequest request) {
        log.info("getConEcmListNoAuth");
        return visaFeign.getConEcmListNoAuth(request);
    }

    @ApiOperation(value = "ID查专家论证会申请")
    @GetMapping("/getConEcmById")
    public BaseResponse getConEcmById(@RequestParam String ecmId) {
        log.info("getConEcmById");
        return visaFeign.getConEcmById(ecmId);
    }
}
