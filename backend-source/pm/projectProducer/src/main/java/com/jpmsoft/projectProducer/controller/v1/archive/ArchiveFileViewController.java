package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/fileView")
public class ArchiveFileViewController {

    @Resource
    private IArchiveFileViewService archiveFileViewService;

    @TenantId
    @PostMapping("/createOrUpdateArchiveFileView")
    public BaseResponse createOrUpdateArchiveFileView(@RequestBody ArchiveFileViewModel request) {
        log.info("createOrUpdateArchiveFileView");
        return archiveFileViewService.createOrUpdateArchiveFileView(request);
    }

    @TenantId
    @PostMapping("/getArchiveFileViewList")
    public BaseResponse getArchiveFileViewList(@RequestBody ArchiveFileViewModel request) {
        log.info("getArchiveFileViewList");
        return archiveFileViewService.getArchiveFileViewList(request);
    }
}
