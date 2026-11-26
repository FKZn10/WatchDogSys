package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "定标清标", value = "定标清标")
@RequestMapping("/v1/calibrationMoon")
public class SecCalibrationMoonController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改定标清标")
    @PostMapping("/createOrUpdateSecCalibrationMoon")
    public BaseResponse createOrUpdateSecCalibrationMoon(@RequestBody SecBrightMoonModel request) {
        log.info("createOrUpdateSecCalibrationMoon");
        return secFeign.createOrUpdateSecCalibrationMoon(request);
    }

    @ApiOperation(value = "分页查询定标清标")
    @PostMapping("/getSecCalibrationMoonList")
    public BaseResponse getSecCalibrationMoonList(@RequestBody SecBrightMoonModel request) {
        log.info("getSecCalibrationMoonList");
        return secFeign.getSecCalibrationMoonList(request);
    }

    @ApiOperation(value = "ID查询定标清标")
    @GetMapping("/getSecCalibrationMoonById")
    public BaseResponse getSecCalibrationMoonById(@RequestParam String sbmId) {
        log.info("getSecCalibrationMoonById");
        return secFeign.getSecCalibrationMoonById(sbmId);
    }
}
