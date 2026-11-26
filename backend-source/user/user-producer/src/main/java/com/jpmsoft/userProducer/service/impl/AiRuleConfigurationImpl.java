package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.AiRuleConfigurationMapper;
import com.jpmsoft.userProducer.pojo.dto.AiRuleConfigurationDTO;
import com.jpmsoft.userProducer.pojo.entity.AiRuleConfiguration;
import com.jpmsoft.userProducer.service.IAiRuleConfigurationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiRuleConfigurationImpl extends ServiceImpl<AiRuleConfigurationMapper, AiRuleConfiguration> implements IAiRuleConfigurationService {
    @Override
    public String generationRulesXml(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("<Rules>");
        if (ValidateUtil.isNotEmpty(type)) {
            LambdaQueryWrapper<AiRuleConfiguration> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AiRuleConfiguration::getType, type);
            wrapper.eq(AiRuleConfiguration::getStatus, "1");
            List<AiRuleConfiguration> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
                data.forEach(d -> {
                    sb.append("<Rule>").append(d.getPrompt()).append("</Rule>");
                });
            }
        }
        sb.append("</Rules>");
        return sb.toString();
    }
    /**
     * 导入规则配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importAiRuleConfiguration(MultipartFile request) {
        try {
            List<AiRuleConfigurationDTO> data = readMultipartFileJson(request);
            List<AiRuleConfiguration> entities = new ArrayList<>();
            data.forEach((n) -> {
                AiRuleConfiguration entity = new AiRuleConfiguration();
                BeanUtils.copyProperties(n, entity);
                if (ValidateUtil.isEmpty(entity.getId())) {
                    entity.setId(GenerateUUID.getUUID());
                }
                entities.add(entity);
            });
            this.saveOrUpdateBatch(entities);
            return BaseResponse.ok();
        } catch (Exception ex) {
            return BaseResponse.fail(ex.getMessage());
        }
    }

    /**
     * 读取JSON文件
     */
    public List<AiRuleConfigurationDTO> readMultipartFileJson(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileBytes);
        List<AiRuleConfigurationDTO> requ = JacksonUtils.parseArray(jsonNode.toString(), AiRuleConfigurationDTO.class);
        return requ;
    }

    /**
     * 导出规则配置
     * @param request
     * @return
     */
    @Override
    public BaseResponse exportAiRuleConfiguration(List<String> request) {
        List<AiRuleConfiguration> data = baseMapper.selectBatchIds(request);
        List<AiRuleConfigurationDTO> returnList = new ArrayList<>();
        data.forEach((n) -> {
            AiRuleConfigurationDTO entity = new AiRuleConfigurationDTO();
            BeanUtils.copyProperties(n, entity);
            returnList.add(entity);
        });
        return BaseResponse.ok(returnList);
    }
}
