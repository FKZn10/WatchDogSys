package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertAuditGroupDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 集团专家评审
 */
@Slf4j
@RestController
@Api(tags = "集团专家评审", value = "集团专家评审")
@RequestMapping("/v1/expert/auditGroup")
public class SecExpertAuditGroupController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或更新集团专家评审")
    @PostMapping("/createOrUpdateSecExpertAuditGroup")
    public BaseResponse createOrUpdateSecExpertAuditGroup(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditGroup");
        return secFeign.createOrUpdateSecExpertAuditGroup(request, token);
    }

    @ApiOperation(value = "创建或更新集团专家评审（定制）")
    @PostMapping("/createOrUpdateSecExpertAuditGroupDz")
    public BaseResponse createOrUpdateSecExpertAuditGroupDz(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditGroupDz");
        return secFeign.createOrUpdateSecExpertAuditGroupDz(request, token);
    }

    @ApiOperation(value = "获取集团专家评审列表")
    @PostMapping("/getSecExpertAuditGroupList")
    public BaseResponse getSecExpertAuditGroupList(@RequestBody SecExpertAuditGroupDTO request) {
        log.info("getSecExpertAuditGroupList");
        return secFeign.getSecExpertAuditGroupList(request);
    }

    @ApiOperation(value = "根据ID获取集团专家评审详情")
    @GetMapping("/getSecExpertAuditGroupById")
    public BaseResponse getSecExpertAuditGroupById(@RequestParam String id) {
        log.info("getSecExpertAuditGroupById");
        return secFeign.getSecExpertAuditGroupById(id);
    }

    @ApiOperation(value = "同步专家信息")
    @GetMapping("/syncSecExpert")
    public BaseResponse syncSecExpert(@RequestParam String id) {
        log.info("syncSecExpert");
        return secFeign.syncSecExpert(id);
    }
}