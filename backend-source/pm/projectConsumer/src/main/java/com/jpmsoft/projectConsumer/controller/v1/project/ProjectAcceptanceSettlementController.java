package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectAcceptanceSettlementModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/projectAcceptanceSettlement")
public class ProjectAcceptanceSettlementController {
    @Autowired
    private PreFeign preFeign;

    @PostMapping("/saveOrUpdateProjectAcceptanceSettlement")
    public BaseResponse saveOrUpdateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request) {
        log.info("saveOrUpdateProjectAcceptanceSettlement");
        return preFeign.saveOrUpdateProjectAcceptanceSettlement(request);
    }
    @PostMapping("/getProjectAcceptanceSettlementList")
    public BaseResponse getProjectAcceptanceSettlementList(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("getProjectAcceptanceSettlementList");
        return preFeign.getProjectAcceptanceSettlementList(request,token);
    }
    @GetMapping("/getProjectAcceptanceSettlementDetail")
    public BaseResponse getProjectAcceptanceSettlementDetail(@RequestParam String id) {
        log.info("getProjectAcceptanceSettlementDetail");
        return preFeign.getProjectAcceptanceSettlementDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectAcceptanceSettlementModel request) {
        log.info("approvalData");
        return preFeign.approvalProjectAcceptanceSettlementData(request);
    }
    @PostMapping("/calculateProjectAcceptanceSettlement")
    public BaseResponse calculateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("calculateProjectAcceptanceSettlement");
        return preFeign.calculateProjectAcceptanceSettlement(request);
    }
    @PostMapping("/calculateProjectAcceptanceSettlementScore")
    public BaseResponse calculateProjectAcceptanceSettlementScore(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("calculateProjectAcceptanceSettlementScore");
        return preFeign.calculateProjectAcceptanceSettlementScore(request);
    }
    @PostMapping("/importProjectAcceptanceSettlement")
    public BaseResponse importProjectAcceptanceSettlement(@RequestPart("files") MultipartFile files, @RequestHeader String token) {
        log.info("importProjectAcceptanceSettlement");
        return preFeign.importProjectAcceptanceSettlement(files,token);
    }
}
