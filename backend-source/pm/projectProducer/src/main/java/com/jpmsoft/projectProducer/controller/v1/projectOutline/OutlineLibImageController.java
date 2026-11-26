package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineLibImageService;
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
@RequestMapping("/v1/projectOutline/image")
public class OutlineLibImageController {
    @Resource
    private IOutlineLibImageService outlineLibImageService;

    /**
     * 初始化图片处理
     *
     * @return
     */
    @GetMapping("/imageInitialization")
    public BaseResponse imageInitialization() {
        log.info("imageInitialization");
        outlineLibImageService.imageInitialization();
        return BaseResponse.ok("图片初始化处理完成");
    }
}