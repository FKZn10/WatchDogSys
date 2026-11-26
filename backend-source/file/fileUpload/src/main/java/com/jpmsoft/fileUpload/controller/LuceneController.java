package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.service.IArchiveFileService;
import com.jpmsoft.fileUpload.utils.ReadFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * lucene全文索引操作
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/24 11:30
 */
@Slf4j
@RestController
@ApiIgnore
@Api(tags = "lucene全文索引", value = "lucene全文索引")
@RequestMapping("/lucene")
public class LuceneController {

    @Resource
    private IArchiveFileService archiveFileService;

    @GetMapping("/fc")
    public BaseResponse fc(@RequestParam String q) throws Exception {
        return BaseResponse.ok(ReadFile.iKSegmenterToList(q));
    }

    /**
     * 创建归档文件的全文索引
     * 单次执行创建100个文件索引
     */
    @ApiIgnore
    @ApiOperation(value = "创建归档文件的全文索引")
    @GetMapping("/createArchiveFileIndex")
    public BaseResponse createArchiveFileIndex() {
        return BaseResponse.ok(archiveFileService.createArchiveFileLuceneIndex());
    }
}
