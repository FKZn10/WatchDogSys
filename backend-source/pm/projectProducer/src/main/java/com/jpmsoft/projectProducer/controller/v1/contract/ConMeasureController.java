package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.service.contract.IConMeasureService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/13 9:38
 */
@Slf4j
@RestController
@RequestMapping("/v1/conMeasure")
public class ConMeasureController {
    @Resource
    private IConMeasureService conMeasureService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createConMeasure")
    public BaseResponse createConMeasure(@RequestBody ConMeasureDto request) {
        log.info("createConMeasure");
        return conMeasureService.createConMeasure(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateConMeasure")
    public BaseResponse createOrUpdateConMeasure(@RequestBody ConMeasureDto request) {
        log.info("createOrUpdateConMeasure");
        return conMeasureService.createOrUpdateConMeasure(request);
    }

    @PostMapping("/updateConMeasure")
    public BaseResponse updateConMeasure(@RequestBody ConMeasureDto request) {
        log.info("updateConMeasure");
        return conMeasureService.updateConMeasure(request);
    }

    @PostMapping("/updateConMeasureOnly")
    public BaseResponse updateConMeasureOnly(@RequestBody ConMeasureDto request) {
        log.info("updateConMeasureOnly");
        return conMeasureService.updateConMeasureOnly(request);
    }

    @TenantId
    @PostMapping("/getConMeasureList")
    public BaseResponse getConMeasureList(@RequestBody ConMeasureDto request,@RequestHeader String token) {
        log.info("getConMeasureList");
        return conMeasureService.getConMeasureList(request,redisUtils.getUid(token));
    }

    @GetMapping("/getConMeasureById")
    public BaseResponse getConMeasureById(@RequestParam String id) {
        log.info("getConMeasureById");
        return conMeasureService.getConMeasureById(id);
    }

    @GetMapping("/getLastApproveMeasureByConId")
    public BaseResponse getLastApproveMeasureByConId(@RequestParam String conId) {
        log.info("getLastApproveMeasureByConId");
        return BaseResponse.ok(conMeasureService.getLastApproveMeasureByConId(conId));
    }

    @GetMapping("/return2Initiator")
    public BaseResponse return2Initiator(@RequestParam String id) {
        log.info("return2Initiator");
        return conMeasureService.return2Initiator(id);
    }

    @GetMapping("/statisticsApplyAndAmt")
    public BaseResponse statisticsApplyAndAmt(@RequestParam String id,@RequestParam(required = false) String conId) {
        log.info("statisticsApplyAndAmt");
        return conMeasureService.statisticsApplyAndAmt(id,conId);
    }

    @GetMapping("/statisticsAmtByConId")
    public BaseResponse statisticsAmtByConId(@RequestParam String conId) {
        log.info("statisticsAmtByConId");
        return conMeasureService.statisticsAmtByConId(conId);
    }

    /**
     * 多条件筛选
     *
     * @param request
     * @return
     */
    @PostMapping("/list/getConMeasureClassInfo")
    public BaseResponse getConMeasureClassInfo(@RequestBody ConMeasureDto request) {
        log.info("getConMeasureClassInfo");
        return conMeasureService.getConMeasureClassInfo(request);
    }

    @PostMapping("/list/getConMeasureInfo")
    public BaseResponse getConMeasureInfo(@RequestBody ConMeasureDto request) {
        log.info("getConMeasureInfo");
        return conMeasureService.getConMeasureInfo(request);
    }

}
