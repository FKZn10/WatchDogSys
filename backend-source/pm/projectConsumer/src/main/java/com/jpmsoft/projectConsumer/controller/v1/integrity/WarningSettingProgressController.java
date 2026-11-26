package com.jpmsoft.projectConsumer.controller.v1.integrity;

import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 预警提醒设置
 */
@Slf4j
@RestController
@RequestMapping("/v1/warningSetting")
public class WarningSettingProgressController {
    @Resource
    private BaseFeign baseFeign;
}