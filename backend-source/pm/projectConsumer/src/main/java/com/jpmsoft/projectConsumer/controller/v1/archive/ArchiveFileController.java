package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "归档文件信息", value = "归档文件信息")
@RequestMapping("/Archive/v1/archiveFile")
public class ArchiveFileController {
    @Autowired
    private ArchiveFeign archiveFeign;

    @ApiOperation(value = "分页查询归档文件信息")
    @PostMapping("/getArchiveFileStatistics")
    public BaseResponse getArchiveFileStatistics(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token) {
        log.info("getArchiveFileStatistics");
        return archiveFeign.getArchiveFileStatistics(request, token);
    }

    /**
     * 按组织人员关系匹配档案目录的部门授权，分页查询归档文件。
     *
     * @param request 文件筛选及分页条件
     * @param token   登录令牌
     * @return 当前页文件及总数
     */
    @ApiOperation(value = "按部门档案目录权限分页查询归档文件")
    @PostMapping("/getArchiveFileStatisticsWZ")
    public BaseResponse getArchiveFileStatisticsWZ(@RequestBody ArchiveStatisticsModel request, @RequestHeader String token) {
        return archiveFeign.getArchiveFileStatisticsWZ(request, token);
    }

    @ApiOperation(value = "修改归档文件信息状态")
    @GetMapping("/updateArchiveFileState")
    public BaseResponse updateArchiveFileState(@RequestParam String id, @RequestParam Integer state) {
        log.info("updateArchiveFileState");
        return archiveFeign.updateArchiveFileState(id, state);
    }

    @ApiOperation(value = "根据ID删除归档文件信息")
    @PostMapping("/deleteArchiveFileById")
    public BaseResponse deleteArchiveFileById(@RequestBody ArchiveStatisticsModel request) {
        log.info("deleteArchiveFileById");
        return archiveFeign.deleteArchiveFileById(request);
    }
}
