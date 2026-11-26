package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 大纲图片文件处理
 *
 * @author tianhelin
 * @date 2025/08/25
 */
@Slf4j
@RestController
@Api(value = "大纲图片文件处理", tags = "大纲图片文件处理")
@RequestMapping("/v1/projectOutline/image")
public class OutlineLibImageController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 初始化图片处理
     *
     * @return
     */
    @ApiOperation("初始化图片处理")
    @GetMapping("/imageInitialization")
    public BaseResponse imageInitialization() {
        log.info("imageInitialization");
        return outlineFeign.imageInitialization();
    }
}
