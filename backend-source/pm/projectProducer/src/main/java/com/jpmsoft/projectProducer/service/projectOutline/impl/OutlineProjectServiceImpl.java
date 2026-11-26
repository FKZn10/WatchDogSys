package com.jpmsoft.projectProducer.service.projectOutline.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.pojo.dto.projectOutline.OutlineLibConfDTO;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 通用大纲服务实现类
 *
 * @author tianhelin
 * @date 2025/07/22
 */
@Slf4j
@Service
public class OutlineProjectServiceImpl implements IOutlineProjectService {
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public OutlineLibConfDTO getOutlineLibConf() {
        String outlineLibConf = sysConfigService.getConfigByCode("outlineLibConf");
        String highLightColor = "red";
        int completeLength = 10;
        int completeLengthExtend = 20;
        if (StrUtil.isNotBlank(outlineLibConf)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(outlineLibConf);
                JsonNode highLightColorNode = jsonNode.get("highLightColor");
                JsonNode completeLengthNode = jsonNode.get("completeLength");
                JsonNode completeLengthExtendNode = jsonNode.get("completeLengthExtend");
                if (highLightColorNode != null && ValidateUtil.isNotEmpty(highLightColorNode.toString())) {
                    // 去除前后引号
                    highLightColor = highLightColorNode.toString().replaceAll("^\"|\"$", "");
                } else {
                    log.warn("highLightColor 配置缺失或无效，使用默认值: {}", highLightColor);
                }
                if (completeLengthNode != null && completeLengthNode.isInt()) {
                    completeLength = completeLengthNode.asInt();
                } else {
                    log.warn("completeLength 配置缺失或无效，使用默认值: {}", completeLength);
                }
                if (completeLengthExtendNode != null && completeLengthExtendNode.isInt()) {
                    completeLengthExtend = completeLengthExtendNode.asInt();
                } else {
                    log.warn("completeLengthExtend 配置缺失或无效，使用默认值: {}", completeLengthExtend);
                }
            } catch (JsonProcessingException e) {
                log.error("解析JSON字符串失败", e);
            }
        }
        return new OutlineLibConfDTO(highLightColor, completeLength, completeLengthExtend);
    }

    @Override
    public String base64Decoding(String content) {
        if (ValidateUtil.isEmpty(content)) {
            return content;
        }
        String config = sysConfigService.getConfigByCode("outlineBase64Encoding");
        if (ValidateUtil.isNotEmpty(config) && "1".equalsIgnoreCase(config) && Base64.isBase64(content)) {
            return Base64.decodeStr(content);
        }
        return content;
    }
}
