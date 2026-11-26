package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "考核评分汇总表", value = "考核评分汇总表")
@RequestMapping("/v1/collect")
public class PeInfoCollectController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改考核评分汇总表")
    @PostMapping("/createOrUpdatePeInfoCollect")
    public BaseResponse createOrUpdatePeInfoCollect(@RequestBody PeInfoCollectRequest request) {
        log.info("createOrUpdatePeInfoCollect");
        return peInfoFeign.createOrUpdatePeInfoCollect(request);
    }

    @ApiOperation(value = "根据ID删除考核评分汇总表")
    @GetMapping("/deletePeInfoCollectByPecId")
    public BaseResponse deletePeInfoCollectByPecId(@RequestParam String pecId) {
        log.info("deletePeInfoCollectByPecId");
        return peInfoFeign.deletePeInfoCollectByPecId(pecId);
    }

    @ApiOperation(value = "更新考核评分汇总表审核状态")
    @PostMapping("/updatePeInfoCollectFlowState")
    public BaseResponse updatePeInfoCollectFlowState(@RequestBody PeInfoCollectRequest request) {
        log.info("updatePeInfoCollectFlowState");
        return peInfoFeign.updatePeInfoCollectFlowState(request);
    }

    @ApiOperation(value = "获取考核评分汇总表列表")
    @PostMapping("/getPeInfoCollectList")
    public BaseResponse getPeInfoCollectList(@RequestBody PeInfoCollectRequest request) {
        log.info("getPeInfoCollectList");
        return peInfoFeign.getPeInfoCollectList(request);
    }

    @ApiOperation(value = "ID获取考核评分汇总表")
    @GetMapping("/getPeInfoCollectByPecId")
    public BaseResponse getPeInfoCollectByPecId(@RequestParam String pecId) {
        log.info("getPeInfoCollectByPecId");
        return peInfoFeign.getPeInfoCollectByPecId(pecId);
    }

    @ApiOperation(value = "ID获取考核评分汇总表及其所有清单数据(导出时)")
    @GetMapping("/getPeInfoCollectExportByPecId")
    public BaseResponse getPeInfoCollectExportByPecId(@RequestParam String pecId, @RequestHeader String token) {
        log.info("getPeInfoCollectExportByPecId");
        return peInfoFeign.getPeInfoCollectExportByPecId(pecId, token);
    }

    @ApiOperation(value = "汇总表退回发起人时 删除清单表")
    @GetMapping("/deletePeInfoCollectDtlWhenBackByPecId")
    public BaseResponse deletePeInfoCollectDtlWhenBackByPecId(@RequestParam String pecId) {
        log.info("deletePeInfoCollectDtlWhenBackByPecId");
        return peInfoFeign.deletePeInfoCollectDtlWhenBackByPecId(pecId);
    }
}
