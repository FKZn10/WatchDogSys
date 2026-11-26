package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminResultRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "定标结果", value = "定标结果")
@RequestMapping("/v1/determinResult")
public class SecDeterminResultController {

    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改定标结果")
    @PostMapping("/createOrUpdateSecDeterminResult")
    public BaseResponse createOrUpdateSecDeterminResult(@RequestBody SecDeterminResultRequest request) {
        log.info("createOrUpdateSecDeterminResult");
        return secFeign.createOrUpdateSecDeterminResult(request);
    }

    @ApiOperation(value = "ID查询定标结果")
    @GetMapping("/getSecDeterminResultById")
    public BaseResponse getSecDeterminResultById(@RequestParam String drId) {
        log.info("getSecDeterminResultById");
        return secFeign.getSecDeterminResultById(drId);
    }

    @ApiOperation(value = "标段id查询审批通过的最新的一条定标信息")
    @GetMapping("/getFirstDeterminResultBySecId")
    public BaseResponse getFirstDeterminResultBySecId(@RequestParam String secId) {
        log.info("getFirstDeterminResultBySecId");
        return secFeign.getFirstDeterminResultBySecId(secId);
    }

    @ApiOperation(value = "分页查询定标结果")
    @PostMapping("/getSecDeterminResultList")
    public BaseResponse getSecDeterminResultList(@RequestBody SecDeterminResultRequest request) {
        log.info("getSecDeterminResultList");
        return secFeign.getSecDeterminResultList(request);
    }
}
