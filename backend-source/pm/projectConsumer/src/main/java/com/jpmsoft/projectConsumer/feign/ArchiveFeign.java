package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveBorrowRequest;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryGroupDto;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.common.model.request.Archive.ArchiveRequest;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案
 */
@FeignClient(name = "project-producer", contextId = "archive")
public interface ArchiveFeign {

    /***********************************************档案目录***************************************************/
    @PostMapping("/v1/archiveDirectory/createOrUpdateArchiveDirectory")
    BaseResponse createOrUpdateArchiveDirectory(@RequestBody ArchiveDirectoryRequest request);

    @PostMapping("/v1/archiveDirectory/getArchiveDirectoryList")
    BaseResponse getArchiveDirectoryList(@RequestBody ArchiveDirectoryRequest request,@RequestHeader String token);

    @GetMapping("/v1/archiveDirectory/upOrDownArchiveDirectory")
    BaseResponse upOrDownArchiveDirectory(@RequestParam String adId, @RequestParam String action,@RequestHeader String token);

    @GetMapping("/v1/archiveDirectory/getArchiveDirectoryByadIdOrpId")
    BaseResponse getArchiveDirectoryByadIdOrpId(@RequestParam String adId, @RequestParam(required = false) String pId, @RequestParam(required = false) String adType,@RequestParam(required = false)String adState,@RequestHeader String token);


    @GetMapping("/v1/archiveDirectory/getArchiveDirectoryTreeByPId")
    BaseResponse getArchiveDirectoryTreeByPId(@RequestParam(required = false) String pId, @RequestParam(required = false) String adType,@RequestHeader String token);

    @GetMapping("/v1/archiveDirectory/refershDirectoryLink")
    BaseResponse refershDirectoryLink(@RequestHeader String token);

    @PostMapping("/v1/archiveDirectory/getAllArchiveDirectoryByPid")
    BaseResponse getAllArchiveDirectoryByPid(@RequestBody ArchiveDirectoryRequest request);

    @GetMapping("/v1/archiveDirectory//delDirectoryByAdId")
    BaseResponse delDirectoryByAdId(@RequestParam String adId);

    /***********************************************归档文档模板***************************************************/
    @GetMapping("/v1/archiveFiletemplate/getArchiveFiletemplateByAdId")
    BaseResponse getArchiveFiletemplateByAdId(@RequestParam String adId);

    /***********************************************项目档案归档信息***************************************************/
    @PostMapping("/v1/archive/createOrUpdateArchive")
    BaseResponse createOrUpdateArchive(@RequestBody ArchiveRequest request);

    @GetMapping("/v1/archive/deleteArchiveByApId")
    BaseResponse deleteArchiveByApId(@RequestParam String apId);

    @PostMapping("/v1/archive/getArchiveList")
    BaseResponse getArchiveList(@RequestBody ArchiveRequest request, @RequestHeader String token);

    /**********************************************档案借阅***************************************************/

    @PostMapping("/v1/archiveBorrow/createOrUpdateArchiveBorrow")
    BaseResponse createOrUpdateArchiveBorrow(@RequestBody ArchiveBorrowRequest request);

    @PostMapping("/v1/archiveBorrow/getArchiveBorrowList")
    BaseResponse getArchiveBorrowList(@RequestBody ArchiveBorrowRequest request);

    @GetMapping("/v1/archiveBorrow/getArchiveBorrowById")
    BaseResponse getArchiveBorrowById(@RequestParam String abId);

    /**********************************************档案文件查看记录***************************************************/
    @PostMapping("/v1/fileView/createOrUpdateArchiveFileView")
    BaseResponse createOrUpdateArchiveFileView(@RequestBody ArchiveFileViewModel request);

    @PostMapping("/v1/fileView/getArchiveFileViewList")
    BaseResponse getArchiveFileViewList(@RequestBody ArchiveFileViewModel request);

    /**********************************************归档文件信息***************************************************/
    @PostMapping("/v1/archiveFile/getArchiveFileStatistics")
    BaseResponse getArchiveFileStatistics(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token);

    /**
     * 按组织人员关系匹配档案目录的部门授权，分页查询归档文件。
     *
     * @param request 文件筛选及分页条件
     * @param token   登录令牌
     * @return 当前页文件及总数
     */
    @PostMapping("/v1/archiveFile/getArchiveFileStatisticsWZ")
    BaseResponse getArchiveFileStatisticsWZ(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token);

    @GetMapping("/v1/archiveFile/updateArchiveFileState")
    BaseResponse updateArchiveFileState(@RequestParam String id, @RequestParam Integer state);

    /**********************************************档案备案申请***************************************************/
    @PostMapping("/v1/filingApply/createOrUpdateArchiveFilingApply")
    BaseResponse createOrUpdateArchiveFilingApply(@RequestBody ArchiveFilingApplyModel request);

    @PostMapping("/v1/filingApply/getArchiveFilingApplyList")
    BaseResponse getArchiveFilingApplyList(@RequestBody ArchiveFilingApplyModel request, @RequestHeader String token);

    @GetMapping("/v1/filingApply/getArchiveFilingApplyById")
    BaseResponse getArchiveFilingApplyById(@RequestParam String faId);
    @GetMapping("/v1/filingApply/synchronizeProject")
    BaseResponse synchronizeProject(@RequestParam String faId);


    /**********************************************档案目录权限 组和用户***************************************************/

    @PostMapping("/v1/archiveDirectoryGroup/createOrUpdateArchiveDirectoryGroupBath")
    BaseResponse createOrUpdateArchiveDirectoryGroupBath(@RequestBody List<ArchiveDirectoryGroupDto> request,@RequestHeader String token);

    @PostMapping("/v1/archiveDirectoryGroup/getArchiveDirectoryGroupList")
    BaseResponse getArchiveDirectoryGroupList(@RequestBody ArchiveDirectoryGroupDto request);
    @PostMapping("/v1/archiveDirectoryGroup/createOrUpdateArchiveDirectoryGroup")
    BaseResponse createOrUpdateArchiveDirectoryGroup(@RequestBody ArchiveDirectoryGroupDto request) ;

    @GetMapping("/v1/archiveDirectoryGroup/getArchiveDirectoryGroupByAdId")
    BaseResponse getArchiveDirectoryGroupByAdId(@RequestParam String adId);

    @GetMapping("/v1/archiveDirectoryGroup/getArchiveDirectoryGroupById")
    BaseResponse getArchiveDirectoryGroupById(@RequestParam String id);

    @GetMapping("/v1/archiveDirectoryGroup/delArchiveDirectoryGroupById")
    BaseResponse delArchiveDirectoryGroupById(@RequestParam String id);

    @PostMapping("/v1/archiveFile/deleteArchiveFileById")
    BaseResponse deleteArchiveFileById(@RequestBody ArchiveStatisticsModel request);
}
