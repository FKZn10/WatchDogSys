package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/archiveFile")
public class ArchiveFileController {

    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getArchiveFileStatistics")
    public BaseResponse getArchiveFileStatistics(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token) {
        log.info("getArchiveFileStatistics");
        return archiveFileService.getArchiveFileStatistics(request, redisUtils.getUid(token));
    }

    /**
     * 按组织人员关系匹配档案目录的部门授权，分页查询归档文件。
     *
     * @param request 文件筛选及分页条件
     * @param token   登录令牌
     * @return 当前页文件及总数
     */
    @PostMapping("/getArchiveFileStatisticsWZ")
    public BaseResponse getArchiveFileStatisticsWZ(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token) {
        return archiveFileService.getArchiveFileStatisticsWZ(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/updateArchiveFileState")
    public BaseResponse updateArchiveFileState(@RequestParam String id, @RequestParam Integer state) {
        log.info("updateArchiveFileState");
        return archiveFileService.updateArchiveFileState(id, state);
    }

    @PostMapping("/deleteArchiveFileById")
    public BaseResponse deleteArchiveFileById(@RequestBody ArchiveStatisticsModel request) {
        log.info("deleteArchiveFileById");
        return archiveFileService.deleteArchiveFileById(request);
    }
}
