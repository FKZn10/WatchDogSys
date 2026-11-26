package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecOpenResultRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "开标结果", value = "开标结果")
@RequestMapping("/v1/openResult")
public class SecOpenResultController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改开标结果")
    @PostMapping("/createOrUpdateSecOpenResult")
    public BaseResponse createOrUpdateSecOpenResult(@RequestBody SecOpenResultRequest request) {
        log.info("createOrUpdateSecOpenResult");
        return secFeign.createOrUpdateSecOpenResult(request);
    }

    @ApiOperation(value = "ID查开标结果")
    @GetMapping("/getSecOpenResultById")
    public BaseResponse getSecOpenResultById(@RequestParam String orId) {
        log.info("getSecOpenResultById");
        return secFeign.getSecOpenResultById(orId);
    }

    @ApiOperation(value = "分页查开标结果")
    @PostMapping("/getSecOpenResultList")
    public BaseResponse getSecOpenResultList(@RequestBody SecOpenResultRequest request) {
        log.info("getSecOpenResultList");
        return secFeign.getSecOpenResultList(request);
    }

    @ApiOperation(value = "分页查开标结果(默认项目标段一对多)")
    @PostMapping("/getSecOpenResultListOne2Many")
    public BaseResponse getSecOpenResultListOne2Many(@RequestBody SecOpenResultRequest request) {
        log.info("getSecOpenResultListOne2Many");
        return secFeign.getSecOpenResultListOne2Many(request);
    }
}
