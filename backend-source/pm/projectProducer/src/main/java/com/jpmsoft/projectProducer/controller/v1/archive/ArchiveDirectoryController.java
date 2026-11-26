package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveDirectoryService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/archiveDirectory")
public class ArchiveDirectoryController {
    @Resource
    private IArchiveDirectoryService archiveDirectoryService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateArchiveDirectory")
    public BaseResponse createOrUpdateArchiveDirectory(@RequestBody ArchiveDirectoryRequest request) {
        log.info("createOrUpdateArchiveDirectory");
        return archiveDirectoryService.createOrUpdateArchiveDirectory(request);
    }

    @TenantId
    @PostMapping("/getArchiveDirectoryList")
    public BaseResponse getArchiveDirectoryList(@RequestBody ArchiveDirectoryRequest request, @RequestHeader String token) {
        //log.info("getArchiveDirectoryList");
        return archiveDirectoryService.getArchiveDirectoryList(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/upOrDownArchiveDirectory")
    public BaseResponse upOrDownArchiveDirectory(@RequestParam String adId, @RequestParam String action, @RequestHeader String token) {
        log.info("upOrDownArchiveDirectory");
        return archiveDirectoryService.upOrDownArchiveDirectory(adId, action, redisUtils.getTeId(token));
    }

    @GetMapping("/getArchiveDirectoryByadIdOrpId")
    public BaseResponse getArchiveDirectoryByadIdOrpId(@RequestParam String adId, @RequestParam(required = false) String pId
            , @RequestParam(required = false) String adType, @RequestHeader String token,@RequestParam(required = false)String adState) {
        log.info("getArchiveDirectoryByadIdOrpId");
        return archiveDirectoryService.getArchiveDirectoryByadIdOrpId(adId, pId, adType,adState, redisUtils.getUserInfoByToken(token));
    }


    @GetMapping("/getArchiveDirectoryTreeByPId")
    public BaseResponse getArchiveDirectoryTreeByPId(@RequestParam(required = false) String pId
            , @RequestParam(required = false) String adType, @RequestHeader String token) {
        log.info("getArchiveDirectoryTreeByPId");
        return archiveDirectoryService.getArchiveDirectoryTreeByPId(pId, adType, redisUtils.getUserInfoByToken(token));
    }


    @GetMapping("/refershDirectoryLink")
    public BaseResponse refershDirectoryLink(@RequestHeader String token) {
        log.info("refershDirectoryLink");
        return archiveDirectoryService.refershDirectoryLink(redisUtils.getTeId(token));
    }


    @TenantId
    @PostMapping("/getAllArchiveDirectoryByPid")
    public BaseResponse getAllArchiveDirectoryByPid(@RequestBody ArchiveDirectoryRequest request) {
        log.info("getAllArchiveDirectoryByPid");
        return archiveDirectoryService.getAllArchiveDirectoryByPid(request);
    }

    @GetMapping("/delDirectoryByAdId")
    public BaseResponse delDirectoryByAdId(@RequestParam String adId, @RequestHeader String token) {
        log.info("delDirectoryByAdId");
        return archiveDirectoryService.delDirectoryByAdId(adId, redisUtils.getTeId(token));
    }

}