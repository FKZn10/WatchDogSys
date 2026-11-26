package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkRecordRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpWorkRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCpWorkRecord")
public class ProjectCpWorkRecordController {
    @Resource
    private IProjectCpWorkRecordService projectCpWorkRecordService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateProCpWorkRecord")
    public BaseResponse createOrUpdateProCpWorkRecord(@RequestBody ProjectCpWorkRecordRequest request) {
        log.info("createOrUpdateProCpWorkRecord");
        return projectCpWorkRecordService.createOrUpdateProCpWorkRecord(request);
    }

    @GetMapping("/deleteProCpWorkRecordById")
    public BaseResponse deleteProCpWorkRecordById(@RequestParam String cpwrId) {
        log.info("deleteProCpWorkRecordById");
        return projectCpWorkRecordService.deleteProCpWorkRecordById(cpwrId);
    }

    @TenantId
    @PostMapping("/getProCpWorkRecordList")
    public BaseResponse getProCpWorkRecordList(@RequestBody ProjectCpWorkRecordRequest request,@RequestHeader String token) {
        log.info("getProCpWorkRecordList");
        return projectCpWorkRecordService.getProCpWorkRecordList(request,redisUtils.getUserInfoByToken(token));
    }
}
