package com.jpmsoft.projectConsumer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractorRecordRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "分包管理-分包单位备案", value = "分包管理-分包单位备案")
@RequestMapping("/sub/v1/subcontractorRecord")
public class SubcontractorRecordController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改分包单位备案")
    @PostMapping("/createOrUpdateSubcontractorRecord")
    public BaseResponse createOrUpdateSubcontractorRecord(@RequestBody SubcontractorRecordRequest request) {
        log.info("createOrUpdateSubcontractorRecord");
        return baseFeign.createOrUpdateSubcontractorRecord(request);
    }

    @ApiOperation(value = "分页查分包单位备案")
    @PostMapping("/getSubcontractorRecordList")
    public BaseResponse getSubcontractorRecordList(@RequestBody SubcontractorRecordRequest request) {
        log.info("getSubcontractorRecordList");
        return baseFeign.getSubcontractorRecordList(request);
    }

    @ApiOperation(value = "ID查分包单位备案")
    @GetMapping("/getSubcontractorRecordBySrId")
    public BaseResponse getSubcontractorRecordBySrId(@RequestParam String srId) {
        log.info("getSubcontractorRecordBySrId");
        return baseFeign.getSubcontractorRecordBySrId(srId);
    }

    @ApiOperation(value = "检查统一社会信用代码是否已存在")
    @GetMapping("/checkCreditCodeIsExist")
    public BaseResponse checkCreditCodeIsExist(@RequestParam String crditCode) {
        log.info("checkCreditCodeIsExist");
        return baseFeign.checkCreditCodeIsExist(crditCode);
    }

    @ApiOperation(value = "审批结束同步单位数据")
    @GetMapping("/syncSubcontractorRecordComapny")
    public BaseResponse syncSubcontractorRecordComapny(@RequestParam String srId) {
        log.info("syncSubcontractorRecordComapny");
        return baseFeign.syncSubcontractorRecordComapny(srId);
    }
}
