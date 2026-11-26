package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInquiryRecordDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryRecordService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQuotationUnitService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 16:12
 */
@Slf4j
@RestController
@RequestMapping("/v1/secInquiryRecord")
public class SecInquiryRecordController {

    @Resource
    private ISecInquiryRecordService secInquiryRecordService;

    @Resource
    private ISecQuotationUnitService secQuotationUnitService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecInquiryRecord")
    public BaseResponse createOrUpdateSecInquiryRecord(@RequestBody SecInquiryRecordDto request) {
        log.info("createOrUpdateSecInquiryRecord");
        return secInquiryRecordService.createOrUpdateSecInquiryRecord(request);
    }

    @TenantId
    @PostMapping("/getSecInquiryRecordListClassInfo")
    public BaseResponse getSecInquiryRecordListClassInfo(@RequestBody SecInquiryRecordDto request, @RequestHeader String token) {
        log.info("getSecInquiryRecordListClassInfo");
        return secInquiryRecordService.getSecInquiryRecordListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecInquiryRecordListClass")
    public BaseResponse getSecInquiryRecordListClass(@RequestBody SecInquiryRecordDto request,@RequestHeader String token) {
        log.info("getSecInquiryRecordListClass");
        return secInquiryRecordService.getSecInquiryRecordListClass(request, redisUtils.getUserInfoByToken(token));
    }
    @TenantId
    @PostMapping("/getSecInquiryRecordList")
    public BaseResponse getSecInquiryRecordList(@RequestBody SecInquiryRecordDto request) {
        log.info("getSecInquiryRecordList");
        return secInquiryRecordService.getSecInquiryRecordList(request);
    }

    @GetMapping("/getSecInquiryRecordById")
    public BaseResponse getSecInquiryRecordById(@RequestParam String id) {
        log.info("getSecInquiryRecordById");
        return secInquiryRecordService.getSecInquiryRecordById(id);
    }

    @GetMapping("/getSecInquiryRecordBySecId")
    public BaseResponse getSecInquiryRecordBySecId(@RequestParam String secId) {
        log.info("getSecInquiryRecordBySecId");
        return secInquiryRecordService.getSecInquiryRecordBySecId(secId);
    }

    /**
     * 查询标段下审批通过的询价记录管理的报价单位
     *
     * @param secId
     * @return
     */
    @GetMapping("/getSecQuotationUnitBySecId")
    public BaseResponse getSecQuotationUnitBySecId(@RequestParam String secId) {
        log.info("getSecQuotationUnitBySecId");
        return secQuotationUnitService.getSecQuotationUnitBySecId(secId);
    }

    @GetMapping("/getNewSecQuotationUnitBySecId")
    public BaseResponse getNewSecQuotationUnitBySecId(@RequestParam String secId) {
        log.info("getNewSecQuotationUnitBySecId");
        return secInquiryRecordService.getNewSecQuotationUnitBySecId(secId);
    }
}
