package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecQePreTrialDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:38
 */
@Slf4j
@RestController
@Api(tags = "资格预审", value = "资格预审")
@RequestMapping("/v1/qePreTrial")
public class SecQePreTrialController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改资格预审")
    @PostMapping("/createOrUpdateSecQePreTrial")
    public BaseResponse createOrUpdateSecQePreTrial(@RequestBody SecQePreTrialDto request) {
        log.info("createOrUpdateSecQePreTrial");
        return secFeign.createOrUpdateSecQePreTrial(request);
    }

    @ApiOperation(value = "ID查询资格预审")
    @GetMapping("/getSecQePreTrialById")
    public BaseResponse getSecQePreTrialById(@RequestParam String id) {
        log.info("getSecQePreTrialById");
        return secFeign.getSecQePreTrialById(id);
    }
}
