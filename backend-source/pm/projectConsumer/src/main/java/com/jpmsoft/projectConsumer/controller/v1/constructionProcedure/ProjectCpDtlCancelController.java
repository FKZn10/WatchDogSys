package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "取消项目建设程序详情", value = "取消项目建设程序详情")
@RequestMapping("/v1/ConstructionProcedure/cpDtlCancel")
public class ProjectCpDtlCancelController {
    @Autowired
    private CpFeign cpFeign;

    @ApiOperation(value = "创建或修改取消建设程序详情记录（创建时标记建设程序详情为不可用状态）")
    @PostMapping("/createOrUpdateProjectCpDtlCancel")
    public BaseResponse createOrUpdateProjectCpDtlCancel(@RequestBody ProjectCpDtlCancelModel request) {
        log.info("createOrUpdateProjectCpDtlCancel");
        return cpFeign.createOrUpdateProjectCpDtlCancel(request);
    }

    @ApiOperation(value = "分页查询取消建设程序详情记录")
    @PostMapping("/getProjectCpDtlCancelList")
    public BaseResponse getProjectCpDtlCancelList(@RequestBody ProjectCpDtlCancelModel request, @RequestHeader String token) {
        return cpFeign.getProjectCpDtlCancelList(request, token);
    }

    @ApiOperation(value = "ID查询取消建设程序详情记录")
    @GetMapping("/getProjectCpDtlCancelById")
    public BaseResponse getProjectCpDtlCancelById(@RequestParam String cdcId) {
        log.info("getProjectCpDtlCancelById");
        return cpFeign.getProjectCpDtlCancelById(cdcId);
    }

    @ApiOperation(value = "作废时启用程序详情")
    @GetMapping("/enableProjectCpDtlStatus")
    public BaseResponse enableProjectCpDtlStatus(@RequestParam String cdcId) {
        log.info("enableProjectCpDtlStatus");
        return cpFeign.enableProjectCpDtlStatus(cdcId);
    }

    @ApiOperation(value = "是否存在正在审批过程中的项目建设程序取消")
    @GetMapping("/existApproveCpCancel")
    public BaseResponse existApproveCpCancel(@RequestParam String cpdId) {
        log.info("existApproveCpCancel");
        return cpFeign.existApproveCpCancel(cpdId);
    }

}
