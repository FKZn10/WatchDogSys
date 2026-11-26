package com.jpmsoft.projectConsumer.controller.v1.archive;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveBorrowRequest;
import com.jpmsoft.projectConsumer.feign.ArchiveFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "档案借阅", value = "档案借阅")
@RequestMapping("/Archive/v1/archiveBorrow")
public class ArchiveBorrowController {
    @Autowired
    private ArchiveFeign archiveFeign;


    @ApiOperation(value = "新增或修改档案借阅")
    @PostMapping("/createOrUpdateArchiveBorrow")
    public BaseResponse createOrUpdateArchiveBorrow(@RequestBody ArchiveBorrowRequest request) {
        log.info("createOrUpdateArchiveBorrow");
        return archiveFeign.createOrUpdateArchiveBorrow(request);
    }

    @ApiOperation(value = "分页查询档案借阅")
    @PostMapping("/getArchiveBorrowList")
    public BaseResponse getArchiveBorrowList(@RequestBody ArchiveBorrowRequest request) {
        log.info("getArchiveBorrowList");
        return archiveFeign.getArchiveBorrowList(request);
    }

    @ApiOperation(value = "ID查询档案借阅")
    @GetMapping("/getArchiveBorrowById")
    public BaseResponse getArchiveBorrowById(@RequestParam String abId) {
        log.info("getArchiveBorrowById");
        return archiveFeign.getArchiveBorrowById(abId);
    }
}
