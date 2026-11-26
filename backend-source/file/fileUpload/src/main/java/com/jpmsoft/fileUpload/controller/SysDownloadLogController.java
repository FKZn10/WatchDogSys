package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.SysDownloadLogDTO;
import com.jpmsoft.fileUpload.service.ISysDownloadLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 10:13
 */
@Slf4j
@RestController
@Api(tags = "文件下载记录", value = "文件下载记录")
@RequestMapping("/downLog")
public class SysDownloadLogController {

    @Resource
    private ISysDownloadLogService sysDownloadLogService;

    @ApiOperation(value = "分页查询文件下载记录")
    @PostMapping("/getDownloadLogList")
    public BaseResponse getDownloadLogList(@RequestBody SysDownloadLogDTO request) {
        return sysDownloadLogService.getDownloadLogList(request);
    }

    @ApiOperation(value = "文件下载记录导出")
    @PostMapping("/ExportDownloadLog")
    public ResponseEntity<byte[]> ExportDownloadLog(@RequestBody SysDownloadLogDTO request, HttpServletResponse response) {
        return sysDownloadLogService.ExportDownloadLog(request,response);
    }
}
