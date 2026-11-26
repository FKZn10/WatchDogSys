package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryGroupDto;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveDirectoryGroupService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:31
 */
@Slf4j
@RestController
@RequestMapping("/v1/archiveDirectoryGroup")
public class ArchiveDirectoryGroupController {

    @Resource
    private IArchiveDirectoryGroupService archiveDirectoryGroupService;
    @Resource
    private RedisUtils redisUtils;


    @PostMapping("/createOrUpdateArchiveDirectoryGroupBath")
    public BaseResponse createOrUpdateArchiveDirectoryGroupBath(@RequestBody List<ArchiveDirectoryGroupDto> request,@RequestHeader String token) {
        log.info("createOrUpdateArchiveDirectoryGroupBath");
        return archiveDirectoryGroupService.createOrUpdateArchiveDirectoryGroupBath(request,redisUtils.getTeId(token));
    }

    @TenantId
    @PostMapping("/getArchiveDirectoryGroupList")
    public BaseResponse getArchiveDirectoryGroupList(@RequestBody ArchiveDirectoryGroupDto request) {
        log.info("getArchiveDirectoryGroupList");
        return archiveDirectoryGroupService.getArchiveDirectoryGroupList(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateArchiveDirectoryGroup")
    public BaseResponse createOrUpdateArchiveDirectoryGroup(@RequestBody ArchiveDirectoryGroupDto request) {
        log.info("createOrUpdateArchiveDirectoryGroup");
        return archiveDirectoryGroupService.createOrUpdateArchiveDirectoryGroup(request);
    }

    @GetMapping("/getArchiveDirectoryGroupByAdId")
    public BaseResponse getArchiveDirectoryGroupByAdId(@RequestParam String adId) {
        log.info("getArchiveDirectoryGroupByAdId");
        return archiveDirectoryGroupService.getArchiveDirectoryGroupByAdId(adId);
    }

    @GetMapping("/getArchiveDirectoryGroupById")
    public BaseResponse getArchiveDirectoryGroupById(@RequestParam String id) {
        log.info("getArchiveDirectoryGroupById");
        return archiveDirectoryGroupService.getArchiveDirectoryGroupById(id);
    }

    @GetMapping("/delArchiveDirectoryGroupById")
    public BaseResponse delArchiveDirectoryGroupById(@RequestParam String id) {
        log.info("delArchiveDirectoryGroupById");
        return archiveDirectoryGroupService.delArchiveDirectoryGroupById(id);
    }
}
