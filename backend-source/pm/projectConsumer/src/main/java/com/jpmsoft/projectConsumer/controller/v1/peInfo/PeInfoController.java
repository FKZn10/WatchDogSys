package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoRankingRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import com.jpmsoft.projectConsumer.pojo.dto.peinfo.PeInfoUnMeetDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "履约评价", value = "履约评价")
@RequestMapping("/v1/peInfo")
public class PeInfoController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改履约评价")
    @PostMapping("/createOrUpdatePeInfo")
    public BaseResponse createOrUpdatePeInfo(@RequestBody PeInfoModel model) {
        log.info("createOrUpdatePeInfo");
        return peInfoFeign.createOrUpdatePeInfo(model);
    }

    @ApiOperation(value = "获取履约评价列表")
    @PostMapping("/getPeInfoList")
    public BaseResponse getPeInfoList(@RequestBody PeInfoModel model, @RequestHeader String token) {
        log.info("getPeInfoList");
        return peInfoFeign.getPeInfoList(model, token);
    }

    @ApiOperation(value = "获取履约评价列表(已经汇总)")
    @PostMapping("/getPeInfoUnMeetApproval")
    public BaseResponse getPeInfoUnMeetApproval(@RequestBody PeInfoUnMeetDto model, @RequestHeader String token) {
        log.info("getPeInfoUnMeetApproval");
        return peInfoFeign.getPeInfoUnMeetApproval(model, token);
    }

    @ApiOperation(value = "获取履约评价列表-不鉴权")
    @PostMapping("/getPeInfoListNoAuth")
    public BaseResponse getPeInfoListNoAuth(@RequestBody PeInfoModel model) {
        log.info("getPeInfoListNoAuth");
        return peInfoFeign.getPeInfoListNoAuth(model);
    }


    @ApiOperation(value = "获取履约评价排名")
    @PostMapping("/getPeInfoRanking")
    public BaseResponse getPeInfoRanking(@RequestBody PeInfoRankingRequest request) {
        log.info("getPeInfoRanking");
        return peInfoFeign.getPeInfoRanking(request);
    }


    @ApiOperation(value = "ID获取履约评价")
    @GetMapping("/getPeInfoByPeId")
    public BaseResponse getPeInfoByPeId(@RequestParam String peId) {
        log.info("getPeInfoByPeId");
        return peInfoFeign.getPeInfoByPeId(peId);
    }

    @ApiOperation(value = "删除履约评价")
    @GetMapping("/deletePeInfoByPeId")
    public BaseResponse deletePeInfoByPeId(@RequestParam String peId) {
        log.info("deletePeInfoByPeId");
        return peInfoFeign.deletePeInfoByPeId(peId);
    }


    @ApiOperation(value = "更新履约评价审批状态")
    @PostMapping("/updateFlowState")
    public BaseResponse updateFlowState(@RequestBody PeInfoModel model) {
        log.info("updateFlowState");
        return peInfoFeign.updateFlowState(model);
    }

    @ApiOperation(value = "履约评价统计")
    @GetMapping("/getPeInfoStatistics")
    public BaseResponse getPeInfoStatistics(@RequestParam(value ="yearStr",required = false) String yearStr) {
        log.info("getPeInfoStatistics");
        return peInfoFeign.getPeInfoStatistics(yearStr);
    }
}
