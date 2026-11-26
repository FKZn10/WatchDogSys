package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "施工管理-工程延期报审", value = "施工管理-工程延期报审")
@RequestMapping("/v1/conDelay")
public class ConDelayController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改工程延期报审")
    @PostMapping("/createOrUpdateConDelay")
    public BaseResponse createOrUpdateConDelay(@RequestBody ConDelayModel request) {
        log.info("createOrUpdateConDelay");
        return baseFeign.createOrUpdateConDelay(request);
    }

    @ApiOperation(value = "分页查询工程延期报审")
    @PostMapping("/getConDelayList")
    public BaseResponse getConDelayList(@RequestBody ConDelayModel request, @RequestHeader String token) {
        log.info("getConDelayList");
        return baseFeign.getConDelayList(request, token);
    }

    @ApiOperation(value = "ID查询工程延期报审")
    @GetMapping("/getConDelayById")
    public BaseResponse getConDelayById(@RequestParam String deId) {
        log.info("getConDelayById");
        return baseFeign.getConDelayById(deId);
    }
}
