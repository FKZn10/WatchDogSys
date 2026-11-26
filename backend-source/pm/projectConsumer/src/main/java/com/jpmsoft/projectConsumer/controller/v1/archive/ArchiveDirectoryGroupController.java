package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryGroupDto;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "档案目录权限-组和用户", value = "档案目录权限-组和用户")
@RequestMapping("/Archive/v1/archiveDirectoryGroup")
public class ArchiveDirectoryGroupController {

    @Resource
    private ArchiveFeign archiveFeign;

    @ApiOperation(value = "批量新增修改档案目录权限-组和用户")
    @PostMapping("/createOrUpdateArchiveDirectoryGroupBath")
    public BaseResponse createOrUpdateArchiveDirectoryGroupBath(@RequestBody List<ArchiveDirectoryGroupDto> request, @RequestHeader String token) {
        log.info("createOrUpdateArchiveDirectoryGroupBath");
        return archiveFeign.createOrUpdateArchiveDirectoryGroupBath(request, token);
    }

    @ApiOperation(value = "分页查询档案目录权限-组和用户")
    @PostMapping("/getArchiveDirectoryGroupList")
    public BaseResponse getArchiveDirectoryGroupList(@RequestBody ArchiveDirectoryGroupDto request) {
        log.info("getArchiveDirectoryGroupList");
        return archiveFeign.getArchiveDirectoryGroupList(request);
    }

    @ApiOperation(value = "新增修改档案目录权限-组和用户")
    @PostMapping("/createOrUpdateArchiveDirectoryGroup")
    public BaseResponse createOrUpdateArchiveDirectoryGroup(@RequestBody ArchiveDirectoryGroupDto request) {
        log.info("createOrUpdateArchiveDirectoryGroup");
        return archiveFeign.createOrUpdateArchiveDirectoryGroup(request);
    }

    @ApiOperation(value = "目录ID查询档案目录权限-组和用户")
    @GetMapping("/getArchiveDirectoryGroupByAdId")
    public BaseResponse getArchiveDirectoryGroupByAdId(@RequestParam String adId) {
        log.info("getArchiveDirectoryGroupByAdId");
        return archiveFeign.getArchiveDirectoryGroupByAdId(adId);
    }

    @ApiOperation(value = "ID查询档案目录权限-组和用户")
    @GetMapping("/getArchiveDirectoryGroupById")
    public BaseResponse getArchiveDirectoryGroupById(@RequestParam String id) {
        log.info("getArchiveDirectoryGroupById");
        return archiveFeign.getArchiveDirectoryGroupById(id);
    }

    @ApiOperation(value = "ID删除档案目录权限-组和用户")
    @GetMapping("/delArchiveDirectoryGroupById")
    public BaseResponse delArchiveDirectoryGroupById(@RequestParam String id) {
        log.info("delArchiveDirectoryGroupById");
        return archiveFeign.delArchiveDirectoryGroupById(id);
    }
}
