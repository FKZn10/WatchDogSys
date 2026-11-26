package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertAuditDTO;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:56
 */
@Slf4j
@RestController
@Api(tags = "评标专家申请", value = "评标专家申请")
@RequestMapping("/v1/expert/audit")
public class SecExpertAuditController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建修改评标专家申请")
    @PostMapping("/createOrUpdateSecExpertAudit")
    public BaseResponse createOrUpdateSecExpertAudit(@RequestBody SecExpertAuditDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAudit");
        return secFeign.createOrUpdateSecExpertAudit(request, token);
    }

    @ApiOperation(value = "创建修改评标专家申请（创建时自动生成名称）")
    @PostMapping("/createOrUpdateSecExpertAuditDz")
    public BaseResponse createOrUpdateSecExpertAuditDz(@RequestBody SecExpertAuditDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditDz");
        return secFeign.createOrUpdateSecExpertAuditDz(request, token);
    }


    @ApiOperation(value = "分页查询评标专家申请")
    @PostMapping("/getSecExpertAuditList")
    public BaseResponse getSecExpertAuditList(@RequestBody SecExpertAuditDTO request) {
        log.info("getSecExpertAuditList");
        return secFeign.getSecExpertAuditList(request);
    }

    @ApiOperation(value = "ID查询评标专家申请")
    @GetMapping("/getSecExpertAuditById")
    public BaseResponse getSecExpertAuditById(@RequestParam String id) {
        log.info("getSecExpertAuditById");
        return secFeign.getSecExpertAuditById(id);
    }

    @ApiOperation(value = "审批通过更新专家状态")
    @GetMapping("/approve2SyncExpertState")
    public BaseResponse approve2SyncExpertState(@RequestParam String id) {
        log.info("approve2SyncExpertState");
        return secFeign.approve2SyncExpertState(id);
    }

    @ApiOperation("企业评标专家申请驳回到发起人")
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTaskAudit");
        return secFeign.returnInitiatorToTaskAudit(request, token);
    }

    @ApiOperation("获取企业已审核的专家信息")
    @GetMapping("/getSecExpertAuditDetailListGroup")
    public BaseResponse getSecExpertAuditDetailListGroup() {
        log.info("getSecExpertAuditDetailListGroup");
        return secFeign.getSecExpertAuditDetailListGroup();
    }

    @ApiOperation("根据年份查询企业提交的专家信息")
    @GetMapping("/getSecExpertAuditByYear")
    public BaseResponse getSecExpertAuditByYear(@RequestParam Integer year) {
        log.info("getSecExpertAuditByYear");
        return secFeign.getSecExpertAuditByYear(year);
    }

    @ApiOperation("企业发起后作废专家相应的流程数据")
    @GetMapping("/invalidExpertProcessData")
    public BaseResponse invalidExpertProcessData(@RequestParam String id) {
        log.info("invalidExpertProcessData");
        return secFeign.invalidExpertProcessData(id);
    }

    @ApiOperation("根据年份查询企业提交的专家信息变更状态")
    @GetMapping("/getSecExpertAuditChangeStatusInfoByYear")
    public BaseResponse getSecExpertAuditChangeStatusInfoByYear(@RequestParam String exApplicantCId, @RequestParam(required = false) Integer year) {
        log.info("getSecExpertAuditChangeStatusInfoByYear");
        return secFeign.getSecExpertAuditChangeStatusInfoByYear(exApplicantCId, year);
    }
}
