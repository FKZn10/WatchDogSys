package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecInquiryRecordDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 16:12
 */
@Slf4j
@RestController
@Api(tags = "询价记录", value = "询价记录")
@RequestMapping("/v1/secInquiryRecord")
public class SecInquiryRecordController {

    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改询价记录")
    @PostMapping("/createOrUpdateSecInquiryRecord")
    public BaseResponse createOrUpdateSecInquiryRecord(@RequestBody SecInquiryRecordDto request) {
        log.info("createOrUpdateSecInquiryRecord");
        return secFeign.createOrUpdateSecInquiryRecord(request);
    }

    @ApiOperation(value = "分页询价记录")
    @PostMapping("/getSecInquiryRecordList")
    public BaseResponse getSecInquiryRecordList(@RequestBody SecInquiryRecordDto request) {
        log.info("getSecInquiryRecordList");
        return secFeign.getSecInquiryRecordList(request);
    }


    @PostMapping("/getSecInquiryRecordListClassInfo")
    public BaseResponse getSecInquiryRecordListClassInfo(@RequestBody SecInquiryRecordDto request, @RequestHeader String token) {
        log.info("getSecInquiryRecordListClassInfo");
        return secFeign.getSecInquiryRecordListClassInfo(request,token);
    }

    @PostMapping("/getSecInquiryRecordListClass")
    public BaseResponse getSecInquiryRecordListClass(@RequestBody SecInquiryRecordDto request,@RequestHeader String token) {
        log.info("getSecInquiryRecordListClass");
        return secFeign.getSecInquiryRecordListClass(request,token);
    }


    @ApiOperation(value = "ID查询询价记录详情")
    @GetMapping("/getSecInquiryRecordById")
    public BaseResponse getSecInquiryRecordById(@RequestParam String id) {
        log.info("getSecInquiryRecordById");
        return secFeign.getSecInquiryRecordById(id);
    }

    @ApiOperation(value = "标段ID查询询价记录（最新审批通过的一条）")
    @GetMapping("/getSecInquiryRecordBySecId")
    public BaseResponse getSecInquiryRecordBySecId(@RequestParam String secId) {
        log.info("getSecInquiryRecordBySecId");
        return secFeign.getSecInquiryRecordBySecId(secId);
    }

    @ApiOperation(value = "查询标段下审批通过的询价记录管理的报价单位")
    @GetMapping("/getSecQuotationUnitBySecId")
    public BaseResponse getSecQuotationUnitBySecId(@RequestParam String secId) {
        log.info("getSecQuotationUnitBySecId");
        return secFeign.getSecQuotationUnitBySecId(secId);
    }

    @ApiOperation(value = "查询标段下审批通过的询价记录管理的报价单位")
    @GetMapping("/getNewSecQuotationUnitBySecId")
    public BaseResponse getNewSecQuotationUnitBySecId(@RequestParam String secId) {
        log.info("getNewSecQuotationUnitBySecId");
        return secFeign.getNewSecQuotationUnitBySecId(secId);
    }
}
