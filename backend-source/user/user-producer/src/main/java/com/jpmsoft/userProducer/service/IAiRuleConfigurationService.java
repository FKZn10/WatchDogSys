package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.AiRuleConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAiRuleConfigurationService extends IService<AiRuleConfiguration> {


    String generationRulesXml(String type);

    /**
     * 导入规则配置
     * @param request
     * @return
     */
    BaseResponse importAiRuleConfiguration(MultipartFile request);

    /**
     * 导出规则配置
     * @param request
     * @return
     */
    BaseResponse exportAiRuleConfiguration(List<String> request);
}
