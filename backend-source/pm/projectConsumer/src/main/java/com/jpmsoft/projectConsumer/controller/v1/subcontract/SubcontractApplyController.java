package com.jpmsoft.projectConsumer.controller.v1.subcontract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractApplyRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "分包管理-分包申请", value = "分包管理-分包申请")
@RequestMapping("/sub/v1/subcontractApply")
public class SubcontractApplyController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改分包申请")
    @PostMapping("/createOrUpdateSubcontractApply")
    public BaseResponse createOrUpdateSubcontractApply(@RequestBody SubcontractApplyRequest request) {
        log.info("createOrUpdateSubcontractApply");
        return baseFeign.createOrUpdateSubcontractApply(request);
    }


    @ApiOperation(value = "分页分包申请")
    @PostMapping("/getSubcontractApplyList")
    public BaseResponse getSubcontractApplyList(@RequestBody SubcontractApplyRequest request) {
        log.info("getSubcontractApplyList");
        return baseFeign.getSubcontractApplyList(request);
    }

    @ApiOperation(value = "ID查分包申请")
    @GetMapping("/getSubcontractApplyBySaId")
    public BaseResponse getSubcontractApplyBySaId(@RequestParam String saId) {
        log.info("getSubcontractApplyBySaId");
        return baseFeign.getSubcontractApplyBySaId(saId);
    }
}
