package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnisRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-工程洽商指令单", value = "变更签证-工程洽商指令单")
@RequestMapping("/v1/conEnis")
public class ConEnisController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改工程洽商指令单")
    @PostMapping("/createOrUpdateConEnis")
    public BaseResponse createOrUpdateConEnis(@RequestBody ConEnisRequest request) {
        log.info("createOrUpdateConEnis");
        return visaFeign.createOrUpdateConEnis(request);
    }

    @ApiOperation(value = "分页查工程洽商指令单")
    @PostMapping("/getConEnisList")
    public BaseResponse getConEnisList(@RequestBody ConEnisRequest request, @RequestHeader String token) {
        log.info("getConEnisList");
        return visaFeign.getConEnisList(request, token);
    }

    @ApiOperation(value = "分页查工程洽商指令单-光明")
    @PostMapping("/getConEnisListGm")
    public BaseResponse getConEnisListGm(@RequestBody ConEnisRequest request, @RequestHeader String token) {
        log.info("getConEnisListGm");
        log.info("请求参数 filterEnisAuth {} currentCompanyId {}", request.getFilterEnisAuth(), request.getCurrentCompanyId());
        return visaFeign.getConEnisListGm(request, token);
    }

    @ApiOperation(value = "分页查工程洽商指令单(不鉴权)")
    @PostMapping("/getConEnisListNoAuth")
    public BaseResponse getConEnisListNoAuth(@RequestBody ConEnisRequest request) {
        log.info("getConEnisListNoAuth");
        return visaFeign.getConEnisListNoAuth(request);
    }

    @ApiOperation(value = "ID查工程洽商指令单")
    @GetMapping("/getConEnisById")
    public BaseResponse getConEnisById(@RequestParam String enisId) {
        log.info("getConEnisById");
        return visaFeign.getConEnisById(enisId);
    }
}
