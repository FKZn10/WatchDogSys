package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecJGExpertAuditDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家入库 （建管）
 */
@Slf4j
@RestController
@RequestMapping("/v1/jgExpert/audit")
public class SecJGExpertAuditController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建修改评标专家申请")
    @PostMapping("/createOrUpdateSecJGExpertAudit")
    public BaseResponse createOrUpdateSecJGExpertAudit(@RequestBody SecJGExpertAuditDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecJGExpertAudit");
        return secFeign.createOrUpdateSecJGExpertAudit(request,token);
    }

    @ApiOperation(value = "创建修改评标专家申请（创建时自动生成名称）")
    @PostMapping("/createOrUpdateSecJGExpertAuditDz")
    public BaseResponse createOrUpdateSecJGExpertAuditDz(@RequestBody SecJGExpertAuditDTO request,@RequestHeader String token) {
        log.info("createOrUpdateSecJGExpertAuditDz");
        return secFeign.createOrUpdateSecJGExpertAuditDz(request,token);
    }

    @ApiOperation(value = "分页查询评标专家申请")
    @PostMapping("/getSecJGExpertAuditList")
    public BaseResponse getSecJGExpertAuditList(@RequestBody SecJGExpertAuditDTO request) {
        log.info("getSecJGExpertAuditList");
        return secFeign.getSecJGExpertAuditList(request);
    }

    @ApiOperation(value = "ID查询评标专家申请")
    @GetMapping("/getSecJGExpertAuditById")
    public BaseResponse getSecJGExpertAuditById(@RequestParam String id) {
        log.info("getSecJGExpertAuditById");
        return secFeign.getSecJGExpertAuditById(id);
    }

    @ApiOperation(value = "审批通过更新专家状态")
    @GetMapping("/approveSyncExpertState")
    public BaseResponse approveSyncExpertState(@RequestParam String id) {
        log.info("approveSyncExpertState");
        return secFeign.approveSyncExpertState(id);
    }

}
