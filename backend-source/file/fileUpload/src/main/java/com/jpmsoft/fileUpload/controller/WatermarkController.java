package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.service.IWatermarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 下载水印
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 14:47
 */
@Slf4j
@RestController
@Api(tags = "下载水印", value = "下载水印")
@RequestMapping("/watermark")
public class WatermarkController {
    @Resource
    private IWatermarkService watermarkService;

    @ApiOperation(value = "获取已启用的图片水印列表")
    @GetMapping("/getWatermarkList")
    public BaseResponse getWatermarkList() {
        return watermarkService.getWatermarkList();
    }
}
