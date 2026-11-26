package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "档案文件查看记录", value = "档案文件查看记录")
@RequestMapping("/Archive/v1/fileView")
public class ArchiveFileViewController {

    @Autowired
    private ArchiveFeign archiveFeign;

    @ApiOperation(value = "新增或修改档案文件查看记录")
    @PostMapping("/createOrUpdateArchiveFileView")
    public BaseResponse createOrUpdateArchiveFileView(@RequestBody ArchiveFileViewModel request) {
        log.info("createOrUpdateArchiveFileView");
        return archiveFeign.createOrUpdateArchiveFileView(request);
    }

    @ApiOperation(value = "分页查询档案文件查看记录")
    @PostMapping("/getArchiveFileViewList")
    public BaseResponse getArchiveFileViewList(@RequestBody ArchiveFileViewModel request) {
        log.info("getArchiveFileViewList");
        return archiveFeign.getArchiveFileViewList(request);
    }
}
