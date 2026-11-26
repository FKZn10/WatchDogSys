package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcoRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-设计变更单", value = "变更签证-设计变更单")
@RequestMapping("/v1/conDco")
public class ConDcoController {
    @Autowired
    private VisaFeign visaFeign;


    @ApiOperation(value = "新增或修改设计变更单")
    @PostMapping("/createOrUpdateConDco")
    public BaseResponse createOrUpdateConDco(@RequestBody ConDcoRequest request) {
        log.info("createOrUpdateConDco");
        return visaFeign.createOrUpdateConDco(request);
    }

    @ApiOperation(value = "分页查询设计变更单")
    @PostMapping("/getConDcoList")
    public BaseResponse getConDcoList(@RequestBody ConDcoRequest request, @RequestHeader String token) {
        log.info("getConDcoList");
        return visaFeign.getConDcoList(request, token);
    }

    @ApiOperation(value = "分页查询设计变更单-光明")
    @PostMapping("/getConDcoListGm")
    public BaseResponse getConDcoListGm(@RequestBody ConDcoRequest request, @RequestHeader String token) {
        log.info("getConDcoListGm");
        return visaFeign.getConDcoListGm(request, token);
    }

    @ApiOperation(value = "分页查询设计变更单-不鉴权")
    @PostMapping("/getConDcoListNoAuth")
    public BaseResponse getConDcoListNoAuth(@RequestBody ConDcoRequest request) {
        log.info("getConDcoListNoAuth");
        return visaFeign.getConDcoListNoAuth(request);
    }


    @ApiOperation(value = "ID查询设计变更单")
    @GetMapping("/getConDcoByDcoId")
    public BaseResponse getConDcoByDcoId(@RequestParam String dcoId) {
        log.info("getConDcoByDcoId");
        return visaFeign.getConDcoByDcoId(dcoId);
    }
}
