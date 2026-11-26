package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecOpenComplaintsDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenComplaintsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 14:13
 */
@Slf4j
@RestController
@RequestMapping("/v1/openComplaints")
public class SecOpenComplaintsController {

    @Resource
    private ISecOpenComplaintsService secOpenComplaintsService;


    @TenantId
    @PostMapping("/createOrUpdateSecOpenComplaints")
    public BaseResponse createOrUpdateSecOpenComplaints(@RequestBody SecOpenComplaintsDto request) {
        log.info("createOrUpdateSecOpenComplaints");
        return secOpenComplaintsService.createOrUpdateSecOpenComplaints(request);
    }

    @TenantId
    @PostMapping("/getSecOpenComplaintsList")
    public BaseResponse getSecOpenComplaintsList(@RequestBody SecOpenComplaintsDto request) {
        log.info("getSecOpenComplaintsList");
        return secOpenComplaintsService.getSecOpenComplaintsList(request);
    }

    @GetMapping("/getSecOpenComplaintsById")
    public BaseResponse getSecOpenComplaintsById(@RequestParam String id) {
        log.info("getSecOpenComplaintsById");
        return secOpenComplaintsService.getSecOpenComplaintsById(id);
    }
}
