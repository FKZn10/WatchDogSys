package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "档案备案申请", value = "档案备案申请")
@RequestMapping("/Archive/v1/filingApply")
public class ArchiveFilingApplyController {
    @Autowired
    private ArchiveFeign archiveFeign;

    @ApiOperation(value = "新增或修改档案备案申请")
    @PostMapping("/createOrUpdateArchiveFilingApply")
    public BaseResponse createOrUpdateArchiveFilingApply(@RequestBody ArchiveFilingApplyModel request) {
        log.info("createOrUpdateArchiveFilingApply");
        return archiveFeign.createOrUpdateArchiveFilingApply(request);
    }

    @ApiOperation(value = "分页查询档案备案申请")
    @PostMapping("/getArchiveFilingApplyList")
    public BaseResponse getArchiveFilingApplyList(@RequestBody ArchiveFilingApplyModel request, @RequestHeader String token) {
        log.info("getArchiveFilingApplyList");
        return archiveFeign.getArchiveFilingApplyList(request, token);
    }

    @ApiOperation(value = "ID查询档案备案申请")
    @GetMapping("/getArchiveFilingApplyById")
    public BaseResponse getArchiveFilingApplyById(@RequestParam String faId) {
        log.info("getArchiveFilingApplyById");
        return archiveFeign.getArchiveFilingApplyById(faId);
    }

    @ApiOperation(value = "根据id将档案备案申请时间同步到项目信息中")
    @GetMapping("/synchronizeProject")
    public BaseResponse synchronizeProject(@RequestParam String faId) {
        log.info("synchronizeProject");
        return archiveFeign.synchronizeProject(faId);
    }
}
