package com.jpmsoft.projectConsumer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionHandoverRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "竣工管理-项目移交", value = "竣工管理-项目移交")
@RequestMapping("/completion/v1/handover")
public class CompletionHandoverController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改项目移交")
    @PostMapping("/createOrUpdateCompletionHandover")
    public BaseResponse createOrUpdateCompletionHandover(@RequestBody CompletionHandoverRequest request) {
        log.info("createOrUpdateCompletionHandover");
        return baseFeign.createOrUpdateCompletionHandover(request);
    }

    @ApiOperation(value = "分页查询项目移交")
    @PostMapping("/getCompletionHandoverList")
    public BaseResponse getCompletionHandoverList(@RequestBody CompletionHandoverRequest request, @RequestHeader String token) {
        log.info("getCompletionHandoverList");
        return baseFeign.getCompletionHandoverList(request, token);
    }

    @ApiOperation(value = "ID查询项目移交")
    @GetMapping("/getCompletionHandoverById")
    public BaseResponse getCompletionHandoverById(@RequestParam String chId) {
        log.info("getCompletionHandoverById");
        return baseFeign.getCompletionHandoverById(chId);
    }

    @ApiOperation(value = "更新建筑信息分项")
    @GetMapping("/updateBuildingInfoSub")
    public BaseResponse updateBuildingInfoSub(@RequestParam String chId) {
        log.info("updateBuildingInfoSub");
        return baseFeign.updateBuildingInfoSub(chId);
    }
    @ApiOperation(value = "重新同步失败的数据")
    @GetMapping("/retrySyncFailedBuildingInfo")
    public BaseResponse retrySyncFailedBuildingInfo() {
        log.info("retrySyncFailedBuildingInfo");
        return baseFeign.retrySyncFailedBuildingInfo();
    }

    /**
     * 定时任务轮询接口：检查质保过期状态
     */
    @ApiOperation(value = "检查质保过期状态")
    @GetMapping("/checkInsuranceStatus")
    public BaseResponse checkInsuranceStatus() {
        log.info("checkInsuranceStatus");
        return baseFeign.checkInsuranceStatus();
    }
}
