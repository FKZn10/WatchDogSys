package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecCalibrationSchemeModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "定标准备方案", value = "定标准备方案")
@RequestMapping("/v1/calibrationScheme")
public class SecCalibrationSchemeController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改定标准备方案")
    @PostMapping("/createOrUpdateSecCalibrationScheme")
    public BaseResponse createOrUpdateSecCalibrationScheme(@RequestBody SecCalibrationSchemeModel request) {
        log.info("createOrUpdateSecCalibrationScheme");
        return secFeign.createOrUpdateSecCalibrationScheme(request);
    }

    @ApiOperation(value = "分页查询定标准备方案")
    @PostMapping("/getSecCalibrationSchemeList")
    public BaseResponse getSecCalibrationSchemeList(@RequestBody SecCalibrationSchemeModel request) {
        log.info("getSecCalibrationSchemeList");
        return secFeign.getSecCalibrationSchemeList(request);
    }

    @ApiOperation(value = "ID查询定标准备方案")
    @GetMapping("/getSecCalibrationSchemeById")
    public BaseResponse getSecCalibrationSchemeById(@RequestParam String scsId) {
        log.info("getSecCalibrationSchemeById");
        return secFeign.getSecCalibrationSchemeById(scsId);
    }
}
