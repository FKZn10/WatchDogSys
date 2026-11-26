package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlCancelService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/cpDtlCancel")
public class ProjectCpDtlCancelController {

    @Resource
    private IProjectCpDtlCancelService projectCpDtlCancelService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateProjectCpDtlCancel")
    public BaseResponse createOrUpdateProjectCpDtlCancel(@RequestBody ProjectCpDtlCancelModel request) {
        log.info("createOrUpdateProjectCpDtlCancel");
        return projectCpDtlCancelService.createOrUpdateProjectCpDtlCancel(request);
    }

    @TenantId
    @PostMapping("/getProjectCpDtlCancelList")
    public BaseResponse getProjectCpDtlCancelList(@RequestBody ProjectCpDtlCancelModel request, @RequestHeader String token) {
        return projectCpDtlCancelService.getProjectCpDtlCancelList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProjectCpDtlCancelById")
    public BaseResponse getProjectCpDtlCancelById(@RequestParam String cdcId) {
        log.info("getProjectCpDtlCancelById");
        return projectCpDtlCancelService.getProjectCpDtlCancelById(cdcId);
    }

    @GetMapping("/enableProjectCpDtlStatus")
    public BaseResponse enableProjectCpDtlStatus(@RequestParam String cdcId) {
        log.info("enableProjectCpDtlStatus");
        return projectCpDtlCancelService.enableProjectCpDtlStatus(cdcId);
    }

    @GetMapping("/existApproveCpCancel")
    public BaseResponse existApproveCpCancel(@RequestParam String cpdId) {
        log.info("existApproveCpCancel");
        return projectCpDtlCancelService.existApproveCpCancel(cpdId);
    }
}
