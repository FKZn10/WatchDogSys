package com.jpmsoft.projectConsumer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractRecordRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "分包管理-分包合同备案", value = "分包管理-分包合同备案")
@RequestMapping("/sub/v1/subcontractRecord")
public class SubcontractRecordController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改分包合同备案")
    @PostMapping("/createOrUpdateSubcontractRecord")
    public BaseResponse createOrUpdateSubcontractRecord(@RequestBody SubcontractRecordRequest request) {
        log.info("createOrUpdateSubcontractRecord");
        return baseFeign.createOrUpdateSubcontractRecord(request);
    }

    @ApiOperation(value = "分页查分包合同备案")
    @PostMapping("/getSubcontractRecordList")
    public BaseResponse getSubcontractRecordList(@RequestBody SubcontractRecordRequest request, @RequestHeader String token) {
        log.info("getSubcontractRecordList");
        return baseFeign.getSubcontractRecordList(request,token);
    }

    @ApiOperation(value = "ID查分包合同备案")
    @GetMapping("/getSubcontractRecordByScId")
    public BaseResponse getSubcontractRecordByScId(@RequestParam String scId) {
        log.info("getSubcontractRecordByScId");
        return baseFeign.getSubcontractRecordByScId(scId);
    }

    @ApiOperation(value = "主合同ID查分包合同备案")
    @GetMapping("/getSubcontractRecordByScMasterConId")
    public BaseResponse getSubcontractRecordByScMasterConId(@RequestParam String scMasterConId) {
        log.info("getSubcontractRecordByScMasterConId");
        return baseFeign.getSubcontractRecordByScMasterConId(scMasterConId);
    }

    @ApiOperation(value = "ID集合查分包合同备案")
    @PostMapping("/getSubcontractRecordByScIds")
    public BaseResponse getSubcontractRecordByScIds(@RequestBody List<String> scIds) {
        log.info("getSubcontractRecordByScIds");
        return baseFeign.getSubcontractRecordByScIds(scIds);
    }

}
