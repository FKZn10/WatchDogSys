package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertChangeModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/secExpertChange")
@Api(tags = "专家库变更记录", value = "专家库变更记录")
public class SecExpertChangeController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家库变更记录")
    @PostMapping("/createOrUpdateSecExpertChange")
    public BaseResponse createOrUpdateSecExpertChange(@RequestBody SecExpertChangeModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertChange");
        return secFeign.createOrUpdateSecExpertChange(request, token);
    }

    @ApiOperation(value = "获取专家库变更记录列表")
    @PostMapping("/getSecExpertChangeList")
    public BaseResponse getSecExpertChangeList(@RequestBody SecExpertChangeModel request) {
        log.info("getSecExpertChangeList");
        return secFeign.getSecExpertChangeList(request);
    }

    @ApiOperation(value = "根据ID获取专家库变更记录详情")
    @GetMapping("/getSecExpertChangeById")
    public BaseResponse getSecExpertChangeById(@RequestParam String id) {
        log.info("getSecExpertChangeById");
        return secFeign.getSecExpertChangeById(id);
    }

    @ApiOperation(value = "同步专家库变更记录数据")
    @GetMapping("/syncSecExpertChangeData")
    public BaseResponse syncSecExpertChangeData(@RequestParam String id) {
        log.info("syncSecExpertChangeData");
        return secFeign.syncSecExpertChangeData(id);
    }

    @ApiOperation("退回变更申请到发起人")
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secFeign.returnInitiatorToTaskChange(request, token);
    }

    /**
     * 更新专家库的变更状态
     *
     * @param id
     * @return
     */
    @ApiOperation("更新专家库的变更状态")
    @GetMapping("/updateChangeStatus")
    public BaseResponse updateChangeStatus(@RequestParam String id) {
        log.info("updateChangeStatus");
        return secFeign.updateChangeStatusChange(id);
    }
}