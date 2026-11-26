package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/archive")
public class ArchiveController {
    @Resource
    private IArchiveService archiveService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateArchive")
    public BaseResponse createOrUpdateArchive(@RequestBody ArchiveRequest request) {
        log.info("createOrUpdateArchive");
        return archiveService.createOrUpdateArchive(request);
    }

    @GetMapping("/deleteArchiveByApId")
    public BaseResponse deleteArchiveByApId(@RequestParam String apId) {
        log.info("deleteArchiveByApId");
        return archiveService.deleteArchiveByApId(apId);
    }

    @TenantId
    @PostMapping("/getArchiveList")
    public BaseResponse getArchiveList(@RequestBody ArchiveRequest request, @RequestHeader String token) {
        log.info("getArchiveList");
        return archiveService.getArchiveList(request, redisUtils.getUid(token));
    }
}
