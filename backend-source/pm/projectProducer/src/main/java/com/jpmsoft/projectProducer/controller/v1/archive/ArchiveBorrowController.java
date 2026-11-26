package com.jpmsoft.projectProducer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveBorrowRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.archive.IArchiveBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/archiveBorrow")
public class ArchiveBorrowController {

    @Resource
    private IArchiveBorrowService archiveBorrowService;


    @TenantId
    @PostMapping("/createOrUpdateArchiveBorrow")
    public BaseResponse createOrUpdateArchiveBorrow(@RequestBody ArchiveBorrowRequest request) {
        log.info("createOrUpdateArchiveBorrow");
        return archiveBorrowService.createOrUpdateArchiveBorrow(request);
    }

    @TenantId
    @PostMapping("/getArchiveBorrowList")
    public BaseResponse getArchiveBorrowList(@RequestBody ArchiveBorrowRequest request) {
        log.info("getArchiveBorrowList");
        return archiveBorrowService.getArchiveBorrowList(request);
    }

    @GetMapping("/getArchiveBorrowById")
    public BaseResponse getArchiveBorrowById(@RequestParam String abId) {
        log.info("getArchiveBorrowById");
        return archiveBorrowService.getArchiveBorrowById(abId);
    }
}
