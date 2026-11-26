package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "施工管理-开工报审", value = "施工管理-开工报审")
@RequestMapping("/v1/conStartApply")
public class ConStartApplyController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改开工报审")
    @PostMapping("/createOrUpdateConStartApply")
    public BaseResponse createOrUpdateConStartApply(@RequestBody ConStartApplyModel request) {
        log.info("createOrUpdateConStartApply");
        return baseFeign.createOrUpdateConStartApply(request);
    }

    @ApiOperation(value = "分页查询开工报审")
    @PostMapping("/getConStartApplyList")
    public BaseResponse getConStartApplyList(@RequestBody ConStartApplyModel request, @RequestHeader String token) {
        log.info("getConStartApplyList");
        return baseFeign.getConStartApplyList(request, token);
    }

    @ApiOperation(value = "ID查询开工报审")
    @GetMapping("/getConStartApplyById")
    public BaseResponse getConStartApplyById(@RequestParam String saId) {
        log.info("getConStartApplyById");
        return baseFeign.getConStartApplyById(saId);
    }

    @ApiOperation(value = "同步开工数据到合同")
    @GetMapping("/syncDataToContract")
    public BaseResponse syncDataToContract(@RequestParam String saId) {
        log.info("syncDataToContract");
        return baseFeign.syncDataToContract(saId);
    }
}
