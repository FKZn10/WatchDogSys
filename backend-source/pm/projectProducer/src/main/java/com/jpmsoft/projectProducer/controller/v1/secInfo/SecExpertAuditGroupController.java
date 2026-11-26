package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 集团专家评审
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/auditGroup")
public class SecExpertAuditGroupController {
    @Resource
    private ISecExpertAuditGroupService secExpertAuditGroupService;

    @PostMapping("/createOrUpdateSecExpertAuditGroup")
    public BaseResponse createOrUpdateSecExpertAuditGroup(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditGroup");
        return secExpertAuditGroupService.createOrUpdateSecExpertAuditGroup(request, token);
    }

    @PostMapping("/createOrUpdateSecExpertAuditGroupDz")
    public BaseResponse createOrUpdateSecExpertAuditGroupDz(@RequestBody SecExpertAuditGroupDTO request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertAuditGroupDz");
        return secExpertAuditGroupService.createOrUpdateSecExpertAuditGroupDz(request, token);
    }

    @PostMapping("/getSecExpertAuditGroupList")
    public BaseResponse getSecExpertAuditGroupList(@RequestBody SecExpertAuditGroupDTO request) {
        log.info("getSecExpertAuditGroupList");
        return secExpertAuditGroupService.getSecExpertAuditGroupList(request);
    }

    @GetMapping("/getSecExpertAuditGroupById")
    public BaseResponse getSecExpertAuditGroupById(@RequestParam String id) {
        log.info("getSecExpertAuditGroupById");
        return secExpertAuditGroupService.getSecExpertAuditGroupById(id);
    }

    @GetMapping("/getSecExpertAuditGroupByYear")
    public BaseResponse getSecExpertAuditGroupByYear(@RequestParam Integer year) {
        log.info("getSecExpertAuditGroupByYear");
        return secExpertAuditGroupService.getSecExpertAuditGroupByYear(year);
    }

    @GetMapping("/syncSecExpert")
    public BaseResponse syncSecExpert(@RequestParam String id) {
        log.info("syncSecExpert");
        return secExpertAuditGroupService.syncSecExpert(id);
    }
}
