package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveFilingApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/filingApply")
public class ArchiveFilingApplyController {

    @Resource
    private IArchiveFilingApplyService archiveFilingApplyService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateArchiveFilingApply")
    public BaseResponse createOrUpdateArchiveFilingApply(@RequestBody ArchiveFilingApplyModel request) {
        log.info("createOrUpdateArchiveFilingApply");
        return archiveFilingApplyService.createOrUpdateArchiveFilingApply(request);
    }

    @TenantId
    @PostMapping("/getArchiveFilingApplyList")
    public BaseResponse getArchiveFilingApplyList(@RequestBody ArchiveFilingApplyModel request, @RequestHeader String token) {
        log.info("getArchiveFilingApplyList");
        return archiveFilingApplyService.getArchiveFilingApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getArchiveFilingApplyById")
    public BaseResponse getArchiveFilingApplyById(@RequestParam String faId) {
        log.info("getArchiveFilingApplyById");
        return archiveFilingApplyService.getArchiveFilingApplyById(faId);
    }
    @GetMapping("/synchronizeProject")
    public BaseResponse synchronizeProject(@RequestParam String faId) {
        log.info("synchronizeProject");
        return archiveFilingApplyService.synchronizeProject(faId);
    }
}
