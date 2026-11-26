package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminDocRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "定标资料", value = "定标资料")
@RequestMapping("/v1/determinDoc")
public class SecDeterminDocController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改定标资料")
    @PostMapping("/createOrUpdateDeterminDoc")
    public BaseResponse createOrUpdateDeterminDoc(@RequestBody SecDeterminDocRequest request) {
        log.info("createOrUpdateDeterminDoc");
        return secFeign.createOrUpdateDeterminDoc(request);
    }

    @ApiOperation(value = "ID作废定标资料")
    @GetMapping("/cancelSecDeterminDocByBdId")
    public BaseResponse cancelSecDeterminDocByBdId(@RequestParam String bdId) {
        log.info("cancelSecDeterminDocByBdId");
        return secFeign.cancelSecDeterminDocByBdId(bdId);
    }

    @ApiOperation(value = "ID查询定标资料")
    @GetMapping("/getSecDeterminDocByBdId")
    public BaseResponse getSecDeterminDocByBdId(@RequestParam String bdId) {
        log.info("getSecDeterminDocByBdId");
        return secFeign.getSecDeterminDocByBdId(bdId);
    }
}
