package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecCalibrationSupervisionDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@Api(tags = "入围定标委员会及监督委员会构成", value = "入围定标委员会及监督委员会构成")
@RequestMapping("/v1/calibrationSupervision")
public class SecCalibrationSupervisionController {

    @Resource
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改入围定标委员会及监督委员会构成")
    @PostMapping("/createOrUpdateSecCalibrationSupervision")
    public BaseResponse createOrUpdateSecCalibrationSupervision(@RequestBody SecCalibrationSupervisionDTO request) {
        log.info("createOrUpdateSecCalibrationSupervision");
        return secFeign.createOrUpdateSecCalibrationSupervision(request);
    }


    @ApiOperation(value = "ID查询入围定标委员会及监督委员会构成")
    @GetMapping("/getSecCalibrationSupervisionById")
    public BaseResponse getSecCalibrationSupervisionById(@RequestParam String id) {
        log.info("getSecCalibrationSupervisionById");
        return secFeign.getSecCalibrationSupervisionById(id);
    }
}
