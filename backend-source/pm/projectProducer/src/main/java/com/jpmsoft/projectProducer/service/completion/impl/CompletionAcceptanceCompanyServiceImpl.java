package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceCompanyDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.completion.CompletionAcceptanceCompanyMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceCompany;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/10 19:36
 */
@Service
public class CompletionAcceptanceCompanyServiceImpl extends ServiceImpl<CompletionAcceptanceCompanyMapper, CompletionAcceptanceCompany> implements ICompletionAcceptanceCompanyService {

    @Override
    public void createOrUpdateCompletionAcceptanceCompany(List<CompletionAcceptanceCompanyDto> datas, String caId) {
        delCompletionAcceptanceCompanyByCaId(caId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<CompletionAcceptanceCompany> entitys = new ArrayList<>();
            datas.forEach(data -> {
                CompletionAcceptanceCompany entity = new CompletionAcceptanceCompany();
                BeanUtils.copyProperties(data, entity);
                entity.setCaId(caId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                entitys.add(entity);
            });
            saveBatch(entitys);
        }
    }

    @Override
    public void delCompletionAcceptanceCompanyByCaId(String caId) {
        QueryWrapper<CompletionAcceptanceCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("Ca_Id", caId);
        baseMapper.delete(wrapper);
    }
}




