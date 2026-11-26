package com.jpmsoft.projectConsumer.controller.v1.yw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.ProjectAcceptanceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projectAcceptance")
@Slf4j
public class ProjectAcceptanceCalculationAuditController {

    @Autowired
    private ProjectAcceptanceFeign projectAcceptanceFeign;
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody ProjectAcceptanceCalculationAuditModel dto) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(dto));
        return projectAcceptanceFeign.saveOrUpdateData(dto);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JacksonUtils.toJSONString(request));
        return projectAcceptanceFeign.approvalData(request);
    }
    @PostMapping("/approvalData2")
    public BaseResponse approvalData2(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JacksonUtils.toJSONString(request));
        return projectAcceptanceFeign.approvalData2(request);
    }


    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProjectAcceptanceCalculationAuditModel request, @RequestHeader String token) {
        log.info("查询列表:{}", JacksonUtils.toJSONString(request));
        return projectAcceptanceFeign.getList(request,token);
    }


    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id")String id) {
        log.info("查询结果:{}", id);
        return projectAcceptanceFeign.getDetail(id);
    }
    @PostMapping("/refreshAll")
    public BaseResponse refreshAll(@RequestBody ProjectAcceptanceCalculationAuditModel request) {
        log.info("审批结果:{}", JacksonUtils.toJSONString(request));
        return projectAcceptanceFeign.refreshAll(request);
    }
}