package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目立项", value = "项目立项")
@RequestMapping("/v1/proApproval")
public class PreProApprovalController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目立项")
    @PostMapping("/createOrUpdatePreProApproval")
    public BaseResponse createOrUpdatePreProApproval(@RequestBody PreProApprovalModel request) {
        log.info("createOrUpdatePreProApproval");
        return preFeign.createOrUpdatePreProApproval(request);
    }

    @ApiOperation(value = "分页查询项目立项")
    @PostMapping("/getPreProApprovalList")
    public BaseResponse getPreProApprovalList(@RequestBody PreProApprovalModel request, @RequestHeader String token) {
        log.info("getPreProApprovalList");
        return preFeign.getPreProApprovalList(request, token);
    }

    @ApiOperation(value = "分页查询项目立项(统计+列表)")
    @PostMapping("/getPreProApprovalStatisticsList")
    public BaseResponse getPreProApprovalStatisticsList(@RequestBody PreProApprovalModel request, @RequestHeader String token) {
        log.info("getPreProApprovalStatisticsList");
        return preFeign.getPreProApprovalStatisticsList(request, token);
    }


    @ApiOperation(value = "项目id集合获取对应估算清单树")
    @PostMapping("/getPreProApprovalDtlByPIds")
    public BaseResponse getPreProApprovalDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getPreProApprovalDtlByPIds");
        return preFeign.getPreProApprovalDtlByPIds(pIds);
    }

    @ApiOperation(value = "ID查询项目立项")
    @GetMapping("/getPreProApprovalById")
    public BaseResponse getPreProApprovalById(@RequestParam String paId) {
        log.info("getPreProApprovalById");
        return preFeign.getPreProApprovalById(paId);
    }

    @ApiOperation(value = "项目ID查询项目立项")
    @GetMapping("/getPreProApprovalByPid")
    public BaseResponse getPreProApprovalByPid(@RequestParam String pId) {
        log.info("getPreProApprovalByPid");
        return preFeign.getPreProApprovalByPid(pId);
    }


    @ApiOperation(value = "ID查询项目立项(项目资金来源（万元）)")
    @GetMapping("/getPreProApprovalWYById")
    public BaseResponse getPreProApprovalWYById(@RequestParam String paId) {
        log.info("getPreProApprovalWYById");
        return preFeign.getPreProApprovalWYById(paId);
    }
}
