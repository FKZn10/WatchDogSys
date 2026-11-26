package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreConstructionPermitService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/constructionPermit")
public class PreConstructionPermitController {

    @Resource
    private IPreConstructionPermitService preConstructionPermitService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreConstructionPermit")
    public BaseResponse createOrUpdatePreConstructionPermit(@RequestBody PreConstructionPermitModel request) {
        log.info("createOrUpdatePreConstructionPermit");
        return preConstructionPermitService.createOrUpdatePreConstructionPermit(request);
    }

    @TenantId
    @PostMapping("/getPreConstructionPermitList")
    public BaseResponse getPreConstructionPermitList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token) {
        log.info("getPreConstructionPermitList");
        return preConstructionPermitService.getPreConstructionPermitList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPreConstructionPermitStatisticsList")
    public BaseResponse getPreConstructionPermitStatisticsList(@RequestBody PreConstructionPermitModel request, @RequestHeader String token) {
        log.info("getPreConstructionPermitList");
        return preConstructionPermitService.getPreConstructionPermitStatisticsList(request, redisUtils.getUid(token));
    }


    @GetMapping("/getPreConstructionPermitById")
    public BaseResponse getPreConstructionPermitById(@RequestParam String cpId) {
        log.info("getPreConstructionPermitById");
        return preConstructionPermitService.getPreConstructionPermitById(cpId);
    }

    @GetMapping("/getPreConstructionPermitUnitListByPid")
    public BaseResponse getPreConstructionPermitUnitListByPid(String pId) {
        log.info("getPreConstructionPermitById");
        return preConstructionPermitService.getPreConstructionPermitUnitListByPid(pId);
    }

    @GetMapping("/approval2RefershProjectInfo")
    public BaseResponse approval2RefershProjectInfo(@RequestParam String cpId) {
        log.info("approval2RefershProjectInfo");
        return preConstructionPermitService.approval2RefershProjectInfo(cpId);
    }
}
