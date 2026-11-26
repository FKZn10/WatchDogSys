package com.jpmsoft.projectProducer.service.projectOutline;

import com.jpmsoft.projectProducer.pojo.dto.projectOutline.OutlineLibConfDTO;

/**
 * 通用大纲服务接口
 *
 * @author tianhelin
 * @date 2025/07/22
 */
public interface IOutlineProjectService {
    /**
     * 获取大纲内容配置
     *
     * @return 大纲内容配置
     */
    OutlineLibConfDTO getOutlineLibConf();

    /**
     * Base64解码
     *
     * @param content 需要解码的内容
     * @return 解码后的内容
     */
    String base64Decoding(String content);
}
