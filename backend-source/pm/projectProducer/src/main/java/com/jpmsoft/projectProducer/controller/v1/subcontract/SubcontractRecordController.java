package com.jpmsoft.projectProducer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractRecordRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/subcontractRecord")
public class SubcontractRecordController {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISubcontractRecordService subcontractRecordService;

    @TenantId
    @PostMapping("/createOrUpdateSubcontractRecord")
    public BaseResponse createOrUpdateSubcontractRecord(@RequestBody SubcontractRecordRequest request) {
        log.info("createOrUpdateSubcontractRecord");
        return subcontractRecordService.createOrUpdateSubcontractRecord(request);
    }


    @TenantId
    @PostMapping("/getSubcontractRecordList")
    public BaseResponse getSubcontractRecordList(@RequestBody SubcontractRecordRequest request, @RequestHeader String token) {
        log.info("getSubcontractRecordList");
        return subcontractRecordService.getSubcontractRecordList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSubcontractRecordByScId")
    public BaseResponse getSubcontractRecordByScId(@RequestParam String scId) {
        log.info("getSubcontractRecordByScId");
        return subcontractRecordService.getSubcontractRecordByScId(scId);
    }



    @GetMapping("/getSubcontractRecordByScMasterConId")
    public BaseResponse getSubcontractRecordByScMasterConId(@RequestParam String scMasterConId) {
        log.info("getSubcontractRecordByScMasterConId");
        return subcontractRecordService.getSubcontractRecordByScMasterConId(scMasterConId);
    }


    @PostMapping("/getSubcontractRecordByScIds")
    public BaseResponse getSubcontractRecordByScIds(@RequestParam List<String> scIds) {
        log.info("getSubcontractRecordByScIds");
        return subcontractRecordService.getSubcontractRecordByScIds(scIds);
    }


}
