package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckDto;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPTSpotCheckService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/qsPTSpotCheck")
public class QsPTSpotCheckController {

    @Resource
    private IQsPTSpotCheckService qsPTSpotCheckService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增修改质量安全抽查
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateQsPTSpotCheck")
    public BaseResponse createOrUpdateQsPTSpotCheck(@RequestBody QsPTSpotCheckDto request) {
        log.info("createOrUpdateQsPTSpotCheck");
        return qsPTSpotCheckService.createOrUpdateQsPTSpotCheck(request);
    }

    @PostMapping("/getQsPTSpotCheckList")
    public BaseResponse getQsPTSpotCheckList(@RequestBody QsPTSpotCheckDto request, @RequestHeader String token) {
        log.info("getQsPTSpotCheckList");
        return qsPTSpotCheckService.getQsPTSpotCheckList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getQsPTSpotCheckListNoAuth")
    public BaseResponse getQsPTSpotCheckListNoAuth(@RequestBody QsPTSpotCheckDto request) {
        log.info("getQsPTSpotCheckListNoAuth");
        return qsPTSpotCheckService.getQsPTSpotCheckListNoAuth(request);
    }

    @GetMapping("/getQsPTSpotCheckById")
    public BaseResponse getQsPTSpotCheckById(@RequestParam String id) {
        log.info("getQsPTSpotCheckById");
        return qsPTSpotCheckService.getQsPTSpotCheckById(id);
    }

    @PostMapping("/approvalSpotCheckData")
    public BaseResponse approvalSpotCheckData(@RequestBody QsPTSpotCheckDto request) {
        return qsPTSpotCheckService.approvalData(request);
    }

}
