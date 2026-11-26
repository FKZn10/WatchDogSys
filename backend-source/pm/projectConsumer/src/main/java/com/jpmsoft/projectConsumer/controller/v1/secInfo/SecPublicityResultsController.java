package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityResultsRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "中标结果公示", value = "中标结果公示")
@RequestMapping("/v1/publicityResults")
public class SecPublicityResultsController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改中标结果公示")
    @PostMapping("/createOrUpdateSecPublicityResults")
    public BaseResponse createOrUpdateSecPublicityResults(@RequestBody SecPublicityResultsRequest request) {
        log.info("createOrUpdateSecPublicityResults");
        return secFeign.createOrUpdateSecPublicityResults(request);
    }

    @ApiOperation(value = "ID查询中标结果公示")
    @GetMapping("/getSecPublicityResultsById")
    public BaseResponse getSecPublicityResultsById(@RequestParam String prId) {
        log.info("getSecPublicityResultsById");
        return secFeign.getSecPublicityResultsById(prId);
    }

    @ApiOperation(value = "分页查询中标结果公示")
    @PostMapping("/getSecPublicityResultsList")
    public BaseResponse getSecPublicityResultsList(@RequestBody SecPublicityResultsRequest request) {
        log.info("getSecPublicityResultsList");
        return secFeign.getSecPublicityResultsList(request);
    }

    @ApiOperation(value = "分页查询中标结果公示(默认项目标段一对多)")
    @PostMapping("/getSecPublicityResultsListOne2Many")
    public BaseResponse getSecPublicityResultsListOne2Many(@RequestBody SecPublicityResultsRequest request) {
        log.info("getSecPublicityResultsListOne2Many");
        return secFeign.getSecPublicityResultsListOne2Many(request);
    }
}
