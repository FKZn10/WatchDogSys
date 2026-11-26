package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectAcceptanceSettlementModel;
import com.jpmsoft.projectProducer.service.project.ProjectAcceptanceSettlementService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/projectAcceptanceSettlement")
public class ProjectAcceptanceSettlementController {
    @Autowired
    private ProjectAcceptanceSettlementService projectAcceptanceSettlementService;
    @Autowired
    private RedisUtils redisUtils;
    @PostMapping("/saveOrUpdateProjectAcceptanceSettlement")
    public BaseResponse saveOrUpdateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request) {
        log.info("saveOrUpdateProjectAcceptanceSettlement");
        return projectAcceptanceSettlementService.saveOrUpdateProjectAcceptanceSettlement(request);
    }
    @PostMapping("/getProjectAcceptanceSettlementList")
    public BaseResponse getProjectAcceptanceSettlementList(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("getProjectAcceptanceSettlementList");
        return projectAcceptanceSettlementService.getProjectAcceptanceSettlementList(request,token);
    }
    @GetMapping("/getProjectAcceptanceSettlementDetail")
    public BaseResponse getProjectAcceptanceSettlementDetail(@RequestParam String id) {
        log.info("getProjectAcceptanceSettlementDetail");
        return projectAcceptanceSettlementService.getProjectAcceptanceSettlementDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectAcceptanceSettlementModel request) {
        log.info("approvalData");
        return projectAcceptanceSettlementService.approvalData(request);
    }
    @PostMapping("/calculateProjectAcceptanceSettlement")
    public BaseResponse calculateProjectAcceptanceSettlement(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("calculateProjectAcceptanceSettlement");
        return projectAcceptanceSettlementService.calculateProjectAcceptanceSettlement(request);
    }
    @PostMapping("/calculateProjectAcceptanceSettlementScore")
    public BaseResponse calculateProjectAcceptanceSettlementScore(@RequestBody ProjectAcceptanceSettlementModel request, @RequestHeader String token) {
        log.info("calculateProjectAcceptanceSettlementScore");
        return projectAcceptanceSettlementService.calculateProjectAcceptanceSettlementScore(request);
    }

    @PostMapping("/importProjectAcceptanceSettlement")
    public BaseResponse importProjectAcceptanceSettlement(@RequestPart("files") MultipartFile files, @RequestHeader String token) {
        log.info("importProjectAcceptanceSettlement");
        return projectAcceptanceSettlementService.importProjectAcceptanceSettlement(files,redisUtils.getUserInfoByToken(token));
    }
}
