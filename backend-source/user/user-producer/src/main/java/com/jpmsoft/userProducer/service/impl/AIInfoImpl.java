package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.AiPromptGenerateDTO;
import com.jpmsoft.common.model.HistoryDialogueDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.AIInfoMapper;
import com.jpmsoft.userProducer.pojo.dto.AIInfoDTO;
import com.jpmsoft.userProducer.pojo.entity.AIInfo;
import com.jpmsoft.userProducer.service.IAIInfoService;
import com.jpmsoft.userProducer.service.IAiRuleConfigurationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AIInfoImpl extends ServiceImpl<AIInfoMapper, AIInfo> implements IAIInfoService {

    @Resource
    private IAiRuleConfigurationService aiRuleConfigurationService;


    @Override
    public String getAiPromptXml(String type) {
        AIInfo info = getAIInfo(type);
        StringBuilder sb = new StringBuilder();
        sb.append("<Instruction>");
        if (ValidateUtil.isNotEmpty(info.getInstruction()))
            sb.append(info.getInstruction());
        sb.append("你必须遵守<Rules>内的规定来生成回答信息");
        sb.append("</Instruction>");
        sb.append(aiRuleConfigurationService.generationRulesXml(type));
        return sb.toString();
    }

    @Override
    public String getAiPromptXml(AiPromptGenerateDTO apg) {
        AIInfo info = getAIInfo(apg.getType());
        StringBuilder sb = new StringBuilder();
        sb.append("<Instruction>");
        if (ValidateUtil.isNotEmpty(info.getInstruction()))
            sb.append(info.getInstruction());
        sb.append("我要问的问题在<Question>内;");
        if (!CollectionUtils.isEmpty(apg.getHistoryDialogue()))
            sb.append("历史问答信息在<HistoryDialogues>内，格式为<historyDialogue>：<Question>：<Answer>：</historyDialogue>");
        sb.append("你必须遵守<Rules>内的规定来生成答案;");
        sb.append("</Instruction>");
        sb.append("<Question>").append(apg.getProblem()).append("</Question>");
        sb.append(aiRuleConfigurationService.generationRulesXml(apg.getType()));
        if (!CollectionUtils.isEmpty(apg.getHistoryDialogue())){
            sb.append("<HistoryDialogues>");
            for (HistoryDialogueDTO h : apg.getHistoryDialogue()) {
                sb.append("<HistoryDialogue>");
                sb.append("<Question>").append(h.getQuestion()).append("</Question>");
                sb.append("<Answer>").append(h.getAnswer()).append("</Answer>");
                sb.append("</HistoryDialogue>");
            }
            sb.append("</HistoryDialogues>");
        }
        return sb.toString();
    }


    /**
     * 根据AI类型类型查询AI配置信息
     * @param type AI类型
     * @return AI配置信息
     */
    @Override
    public AIInfo getAIInfo(String type) {
        LambdaQueryWrapper<AIInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AIInfo::getAiType, type);
        wrapper.eq(AIInfo::getStatus, "1");
        List<AIInfo> list = this.list(wrapper);
        if (CollectionUtils.isEmpty(list)) throw new JPMException("未知AI信息！");
        return list.stream().findFirst().get();
    }

    /**
     * 导入AI配置信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importAiInfo(MultipartFile request) {
        try {
            List<AIInfoDTO> data = readMultipartFileJson(request);
            List<AIInfo> entities = new ArrayList<>();
            data.forEach((n) -> {
                AIInfo entity = new AIInfo();
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
    public List<AIInfoDTO> readMultipartFileJson(MultipartFile file) throws IOException {
        // 读取文件内容
        byte[] fileBytes = file.getBytes();
        // 解析JSON数据
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileBytes);
        List<AIInfoDTO> list = JacksonUtils.parseArray(jsonNode.toString(), AIInfoDTO.class);
        return list;
        // 处理JSON数据
        // ...
    }

    /**
     * 导出AI配置信息
     * @param request ID列表
     * @return
     */
    @Override
    public BaseResponse exportAiInfo(List<String> request) {
        if (CollectionUtils.isEmpty(request)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }

        List<AIInfo> dataList = baseMapper.selectBatchIds(request);
        
        if (CollectionUtils.isEmpty(dataList)) {
            return BaseResponse.ok(new ArrayList<>());
        }

        List<AIInfoDTO> resultList = new ArrayList<>();
        for (AIInfo entity : dataList) {
            AIInfoDTO dto = new AIInfoDTO();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return BaseResponse.ok(resultList);
    }
}
