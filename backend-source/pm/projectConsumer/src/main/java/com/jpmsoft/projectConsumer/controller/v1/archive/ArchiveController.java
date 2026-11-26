package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveRequest;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目档案归档信息", value = "项目档案归档信息")
@RequestMapping("/Archive/v1/archive")
public class ArchiveController {
    @Autowired
    private ArchiveFeign archiveFeign;


    @ApiOperation(value = "新增或修改项目档案归档信息")
    @PostMapping("/createOrUpdateArchive")
    public BaseResponse createOrUpdateArchive(@RequestBody ArchiveRequest request) {
        log.info("createOrUpdateArchive");
        return archiveFeign.createOrUpdateArchive(request);
    }

    @ApiOperation(value = "ID删除档案归档信息（仅删除记录，不删除文件）")
    @GetMapping("/deleteArchiveByApId")
    public BaseResponse deleteArchiveByApId(@RequestParam String apId) {
        log.info("deleteArchiveByApId");
        return archiveFeign.deleteArchiveByApId(apId);
    }

    @ApiOperation(value = "分页查询档案归档信息")
    @PostMapping("/getArchiveList")
    public BaseResponse getArchiveList(@RequestBody ArchiveRequest request, @RequestHeader String token) {
        log.info("getArchiveList");
        return archiveFeign.getArchiveList(request, token);
    }
}
