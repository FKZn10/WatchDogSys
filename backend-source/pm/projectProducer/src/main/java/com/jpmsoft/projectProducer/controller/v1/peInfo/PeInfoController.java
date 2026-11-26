package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoRankingRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeInfoUnMeetDto;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/peInfo")
public class PeInfoController {

    @Resource
    private IPeInfoService peInfoService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeInfo")
    public BaseResponse createOrUpdatePeInfo(@RequestBody PeInfoModel model) {
        log.info("createOrUpdatePeInfo");
        return peInfoService.createOrUpdatePeInfo(model);
    }

    @TenantId
    @PostMapping("/getPeInfoList")
    public BaseResponse getPeInfoList(@RequestBody PeInfoModel model, @RequestHeader String token) {
        log.info("getPeInfoList");
        return peInfoService.getPeInfoList(model, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPeInfoUnMeetApproval")
    public BaseResponse getPeInfoUnMeetApproval(@RequestBody PeInfoUnMeetDto model, @RequestHeader String token) {
        log.info("getPeInfoUnMeetApproval");
        return peInfoService.getPeInfoUnMeetApproval(model, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPeInfoListNoAuth")
    public BaseResponse getPeInfoListNoAuth(@RequestBody PeInfoModel model) {
        log.info("getPeInfoListNoAuth");
        return peInfoService.getPeInfoListNoAuth(model);
    }


    @TenantId
    @PostMapping("/getPeInfoRanking")
    public BaseResponse getPeInfoRanking(@RequestBody PeInfoRankingRequest request) {
        log.info("getPeInfoRanking");
        return peInfoService.getPeInfoRanking(request);
    }


    @GetMapping("/getPeInfoByPeId")
    public BaseResponse getPeInfoByPeId(@RequestParam String peId) {
        log.info("getPeInfoByPeId");
        return peInfoService.getPeInfoByPeId(peId);
    }

    @GetMapping("/deletePeInfoByPeId")
    public BaseResponse deletePeInfoByPeId(@RequestParam String peId) {
        log.info("deletePeInfoListByPeId");
        return peInfoService.deletePeInfoByPeId(peId);
    }

    @GetMapping("/getPeInfoStatistics")
    public BaseResponse getPeInfoStatistics(@RequestParam(value ="yearStr",required = false) String yearStr) {
        log.info("getPeInfoStatistics");
        return peInfoService.getPeInfoStatistics(yearStr);
    }
    @PostMapping("/updateFlowState")
    public BaseResponse updateFlowState(@RequestBody PeInfoModel model) {
        log.info("updateFlowState");
        return peInfoService.updateFlowState(model);
    }
}
