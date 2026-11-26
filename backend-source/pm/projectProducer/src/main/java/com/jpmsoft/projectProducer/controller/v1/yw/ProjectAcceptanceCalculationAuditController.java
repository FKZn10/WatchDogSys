package com.jpmsoft.projectProducer.controller.v1.yw;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import com.jpmsoft.projectProducer.service.yw.ProjectAcceptanceCalculationAuditService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projectAcceptance")
@Slf4j
public class ProjectAcceptanceCalculationAuditController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ProjectAcceptanceCalculationAuditService projectAcceptanceCalculationAuditService;
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody ProjectAcceptanceCalculationAuditModel dto) {
        return projectAcceptanceCalculationAuditService.saveOrUpdateData(dto);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return projectAcceptanceCalculationAuditService.approvalData(request);
    }

    @PostMapping("/approvalData2")
    public BaseResponse approvalData2(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return projectAcceptanceCalculationAuditService.approvalData2(request);
    }
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProjectAcceptanceCalculationAuditModel request, @RequestHeader String token) {
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return projectAcceptanceCalculationAuditService.getList(request,redisUtils.getUid(token));
    }


    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id")String id) {
        log.info("查询结果:{}", id);
        return projectAcceptanceCalculationAuditService.getDetail(id);
    }

    @PostMapping("/refreshAll")
    public BaseResponse refreshAll(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return projectAcceptanceCalculationAuditService.refreshAll(request);
    }
}