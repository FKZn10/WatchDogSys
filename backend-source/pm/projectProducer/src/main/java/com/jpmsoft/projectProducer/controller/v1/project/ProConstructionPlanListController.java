package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IProConstructionPlanListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proConstPlanList")
public class ProConstructionPlanListController {

    @Resource
    private IProConstructionPlanListService proConstructionPlanListService;


    @TenantId
    @PostMapping("/createOrUpdateProConstructionPlanList")
    public BaseResponse createOrUpdateProConstructionPlanList(@RequestBody ProConstructionPlanListModel request) {
        log.info("createOrUpdateProConstructionPlanList");
        return proConstructionPlanListService.createOrUpdateProConstructionPlanList(request);
    }

    @TenantId
    @PostMapping("/getProConstructionPlanList")
    public BaseResponse getProConstructionPlanList(@RequestBody ProConstructionPlanListModel request) {
        log.info("getProConstructionPlanList");
        return proConstructionPlanListService.getProConstructionPlanList(request);
    }

    @TenantId
    @PostMapping("/getProConstructionPlanSelectList")
    public BaseResponse getProConstructionPlanSelectList(@RequestBody ProConstructionPlanListModel request) {
        log.info("getProConstructionPlanSelectList");
        return proConstructionPlanListService.getProConstructionPlanSelectList(request);
    }

    @GetMapping("/getProConstructionPlanListById")
    public BaseResponse getProConstructionPlanListById(@RequestParam String cplId) {
        log.info("getProConstructionPlanListById");
        return proConstructionPlanListService.getProConstructionPlanListById(cplId);
    }

    @GetMapping("/getProConstructionPlanListByPcpId")
    public BaseResponse getProConstructionPlanListByPcpId(@RequestParam String pcpId) {
        log.info("getProConstructionPlanListByPcpId");
        return proConstructionPlanListService.getProConstructionPlanListByPcpId(pcpId);
    }
}
