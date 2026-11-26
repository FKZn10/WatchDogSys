package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.AiPromptGenerateDTO;
import com.jpmsoft.userProducer.pojo.entity.AIInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAIInfoService extends IService<AIInfo> {



    String  getAiPromptXml(String type);

    String  getAiPromptXml(AiPromptGenerateDTO apg);

    /**
     * 导入AI配置信息
     * @param request
     * @return
     */
    BaseResponse importAiInfo(MultipartFile request);

    /**
     * 导出AI配置信息
     * @param request
     * @return
     */
    BaseResponse exportAiInfo(List<String> request);

    /**
     * 根据AI类型类型查询AI配置信息
     * @param type AI类型
     * @return AI配置信息
     */
    AIInfo getAIInfo(String type);
}
