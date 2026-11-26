package com.jpmsoft.projectConsumer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupDisclosureRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "监理管理-工程交底", value = "监理管理-工程交底")
@RequestMapping("/v1/conSupDisclosure")
public class ConSupDisclosureController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改现场签证变更记录")
    @PostMapping("/createOrUpdateConSupDisclosure")
    public BaseResponse createOrUpdateConSupDisclosure(@RequestBody ConSupDisclosureRequest request) {
        log.info("createOrUpdateConSupDisclosure");
        return baseFeign.createOrUpdateConSupDisclosure(request);
    }

    @PostMapping("/getConSupDisclosureList")
    public BaseResponse getConSupDisclosureList(@RequestBody ConSupDisclosureRequest request, @RequestHeader String token) {
        log.info("getConSupDisclosureList");
        return baseFeign.getConSupDisclosureList(request, token);
    }

    @GetMapping("/getConSupDisclosureById")
    public BaseResponse getConSupDisclosureById(@RequestParam String disId) {
        log.info("getConSupDisclosureById");
        return baseFeign.getConSupDisclosureById(disId);
    }
}
