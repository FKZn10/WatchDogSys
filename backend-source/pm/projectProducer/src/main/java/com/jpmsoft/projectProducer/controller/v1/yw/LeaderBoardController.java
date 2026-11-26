package com.jpmsoft.projectProducer.controller.v1.yw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.loadBoard.*;
import com.jpmsoft.projectProducer.service.leaderBoard.LeaderBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/yw/leaderBoard")
@Slf4j
public class LeaderBoardController {
    @Autowired
    private LeaderBoardService leaderBoardService;

    @PostMapping("/getPreProjectInfo")
    public BaseResponse getPreProjectInfo(@RequestBody ProjectLoadRequest request) {
        log.info("getPreProjectInfo");
        return leaderBoardService.getPreProjectInfo(request);
    }
    @PostMapping("/getPreProjectInfoList")
    public BaseResponse getPreProjectInfoList(@RequestBody ProjectLoadRequest request) {
        log.info("getPreProjectInfoList");
        return leaderBoardService.getPreProjectInfoList(request);
    }

    @PostMapping("/getContractInfo")
    public BaseResponse getContractInfo(@RequestBody ContractLoadRequest request) {
        log.info("getContractInfo");
        return leaderBoardService.getContractInfo(request);
    }
    @PostMapping("/getContractInfoList")
    public BaseResponse getContractInfoList(@RequestBody ContractLoadRequest request) {
        log.info("getContractInfoList");
        return leaderBoardService.getContractInfoList(request);
    }

    @PostMapping("/getSiteManagementInfo")
    public BaseResponse getSiteManagementInfo(@RequestBody SiteManagementRequest request) {
        log.info("getSiteManagementInfo");
        return leaderBoardService.getSiteManagementInfo(request);
    }
    @PostMapping("/getSiteManagementInfoCompare")
    public BaseResponse getSiteManagementInfoCompare(@RequestBody SiteManagementRequest request) {
        log.info("getSiteManagementInfoCompare");
        return leaderBoardService.getSiteManagementInfoCompare(request);
    }

    @PostMapping("/getSiteManagementInfoList")
    public BaseResponse getSiteManagementInfoList(@RequestBody SiteManagementRequest request) {
        log.info("getSiteManagementInfoList");
        return leaderBoardService.getSiteManagementInfoList(request);
    }

    //支付管理
    @PostMapping("/getPayInfo")
    public BaseResponse getPayInfo(@RequestBody PayLoadRequest request) {
        log.info("getPayInfo");
        return leaderBoardService.getPayInfo(request);
    }

    @PostMapping("/getPayInfoList")
    public BaseResponse getPayInfoList(@RequestBody PayLoadRequest request) {
        log.info("getPayInfoList");
        return leaderBoardService.getPayInfoList(request);
    }

    //资金计划和支付跟踪
    @PostMapping("/getPlanInfo")
    public BaseResponse getPlanInfo(@RequestBody PlanLoadRequest request) {
        log.info("getPlanInfo");
        return leaderBoardService.getPlanInfo(request);
    }
    @PostMapping("/getPlanInfoList")
    public BaseResponse getPlanInfoList(@RequestBody PlanLoadRequest request) {
        log.info("getPlanInfoList");
        return leaderBoardService.getPlanInfoList(request);
    }
}