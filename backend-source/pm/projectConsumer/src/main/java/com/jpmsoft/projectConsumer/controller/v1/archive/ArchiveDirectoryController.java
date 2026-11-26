package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "档案目录", value = "档案目录")
@RequestMapping("/Archive/v1/archiveDirectory")
public class ArchiveDirectoryController {
    @Autowired
    private ArchiveFeign archiveFeign;


    @ApiOperation(value = "新增或修改档案目录")
    @PostMapping("/createOrUpdateArchiveDirectory")
    public BaseResponse createOrUpdateArchiveDirectory(@RequestBody ArchiveDirectoryRequest request) {
        log.info("createOrUpdateArchiveDirectory");
        return archiveFeign.createOrUpdateArchiveDirectory(request);
    }

    @ApiOperation(value = "查询档案目录")
    @PostMapping("/getArchiveDirectoryList")
    public BaseResponse getArchiveDirectoryList(@RequestBody ArchiveDirectoryRequest request, @RequestHeader String token) {
        log.info("getArchiveDirectoryList");
        return archiveFeign.getArchiveDirectoryList(request, token);
    }

    @ApiOperation(value = "上移或下移")
    @GetMapping("/upOrDownArchiveDirectory")
    public BaseResponse upOrDownArchiveDirectory(@RequestParam String adId, @RequestParam String action, @RequestHeader String token) {
        log.info("upOrDownArchiveDirectory");
        return archiveFeign.upOrDownArchiveDirectory(adId, action, token);
    }


    @ApiOperation(value = "项目ID+档案ID查询子集（包含归档信息数量）")
    @GetMapping("/getArchiveDirectoryByadIdOrpId")
    public BaseResponse getArchiveDirectoryByadIdOrpId(@RequestParam String adId, @RequestParam(required = false) String pId
            , @RequestParam(required = false) String adType, @RequestHeader String token,@RequestParam(required = false)String adState){
        log.info("getArchiveDirectoryByadIdOrpId");
        return archiveFeign.getArchiveDirectoryByadIdOrpId(adId, pId, adType,adState, token);
    }

    @ApiOperation(value = "项目ID+档案ID查询归档目录树，上级统计下级归档信息数量")
    @GetMapping("/getArchiveDirectoryTreeByPId")
    public BaseResponse getArchiveDirectoryTreeByPId(@RequestParam(required = false) String pId
            , @RequestParam(required = false) String adType, @RequestHeader String token) {
        log.info("getArchiveDirectoryTreeByPId");
        return archiveFeign.getArchiveDirectoryTreeByPId(pId, adType, token);
    }


    @ApiOperation(value = "刷新档案目录链路")
    @GetMapping("/refershDirectoryLink")
    public BaseResponse refershDirectoryLink(@RequestHeader String token) {
        log.info("refershDirectoryLink");
        return archiveFeign.refershDirectoryLink(token);
    }


    @ApiOperation(value = "查询档案目录(项目筛选，统计已归档数量)，还可根据归档目录名筛选，排除无归档数量节点")
    @PostMapping("/getAllArchiveDirectoryByPid")
    public BaseResponse getAllArchiveDirectoryByPid(@RequestBody ArchiveDirectoryRequest request) {
        log.info("getAllArchiveDirectoryByPid");
        return archiveFeign.getAllArchiveDirectoryByPid(request);
    }


    @ApiOperation(value = "ID删除目录")
    @GetMapping("/delDirectoryByAdId")
    public BaseResponse delDirectoryByAdId(@RequestParam String adId) {
        log.info("delDirectoryByAdId");
        return archiveFeign.delDirectoryByAdId(adId);
    }
}
