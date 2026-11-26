package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceSectionDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.completion.CompletionAcceptanceSectionMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceSection;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceSectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:38
 */
@Service
public class CompletionAcceptanceSectionImpl extends ServiceImpl<CompletionAcceptanceSectionMapper, CompletionAcceptanceSection>
        implements ICompletionAcceptanceSectionService {
    @Override
    public void createOrUpdateCompletionAcceptanceSection(List<CompletionAcceptanceSectionDto> datas, String caId) {
        delCompletionAcceptanceSectionByCaId(caId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<CompletionAcceptanceSection> entitys = datas.stream().map(data -> {
                CompletionAcceptanceSection entity = new CompletionAcceptanceSection();
                BeanUtils.copyProperties(data, entity);
                entity.setCaId(caId);
                entity.setCasId(ValidateUtil.isEmpty(data.getCasId()) ? GenerateUUID.getUUID() : data.getCasId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delCompletionAcceptanceSectionByCaId(String caId) {
        QueryWrapper<CompletionAcceptanceSection> wrapper = new QueryWrapper<>();
        wrapper.eq("Ca_Id", caId);
        baseMapper.delete(wrapper);
    }
}
